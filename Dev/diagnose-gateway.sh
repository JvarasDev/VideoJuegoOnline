#!/usr/bin/env bash
# ==============================================================================
# diagnose-gateway.sh — VideoJuegoOnline
# Script de diagnóstico completo para el 404 en http://localhost:8080/api/pagos
# Uso: bash diagnose-gateway.sh
# ==============================================================================

RED='\033[0;31m'; GREEN='\033[0;32m'; YELLOW='\033[1;33m'; NC='\033[0m'; BOLD='\033[1m'

ok()   { echo -e "  ${GREEN}✅ $1${NC}"; }
fail() { echo -e "  ${RED}❌ $1${NC}"; }
warn() { echo -e "  ${YELLOW}⚠️  $1${NC}"; }
title(){ echo -e "\n${BOLD}━━━ $1 ━━━${NC}"; }

# ─────────────────────────────────────────────────────────────
# 1. CONFIG-SERVER responde y sirve la config del gateway
# ─────────────────────────────────────────────────────────────
title "1. Config-Server → ¿sirve rutas del gateway?"

CS_HEALTH=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8888/actuator/health)
if [ "$CS_HEALTH" == "200" ]; then
  ok "Config-server health OK (HTTP $CS_HEALTH)"
else
  fail "Config-server NO responde (HTTP $CS_HEALTH)"
  warn "Verificar: docker logs config-server --tail 50"
fi

echo ""
echo "  Rutas que el config-server sirve al gateway:"
curl -s http://localhost:8888/api-gateway/default | \
  python3 -c "
import sys, json
try:
    d = json.load(sys.stdin)
    sources = d.get('propertySources', [])
    for s in sources:
        name = s.get('name', '')
        props = s.get('source', {})
        routes = {k: v for k, v in props.items() if 'routes' in k.lower()}
        if routes:
            print(f'  Fuente: {name}')
            for k, v in list(routes.items())[:10]:
                print(f'    {k}: {v}')
except: print('  (no se pudo parsear la respuesta)')
" 2>/dev/null || echo "  (python3 no disponible — ver respuesta cruda abajo)"

curl -s http://localhost:8888/api-gateway/default | grep -E "Path|uri|pago|StripPrefix" | head -20

# ─────────────────────────────────────────────────────────────
# 2. GATEWAY recibió las rutas del config-server
# ─────────────────────────────────────────────────────────────
title "2. Gateway → ¿rutas cargadas correctamente?"

GW_HEALTH=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/actuator/health)
if [ "$GW_HEALTH" == "200" ]; then
  ok "Gateway health OK (HTTP $GW_HEALTH)"
else
  fail "Gateway NO responde en :8080 (HTTP $GW_HEALTH)"
fi

echo ""
echo "  Rutas activas en el gateway:"
ROUTES=$(curl -s http://localhost:8080/actuator/gateway/routes 2>/dev/null)
if echo "$ROUTES" | grep -q "pago"; then
  ok "Ruta pago-service ENCONTRADA en el gateway"
  echo "$ROUTES" | grep -A5 '"pago"' | head -20
else
  fail "Ruta pago-service NO encontrada en el gateway"
  echo "  Rutas disponibles:"
  echo "$ROUTES" | grep '"route_id"' | head -15
fi

echo ""
echo "  ¿El predicado usa /api/pagos o /pago-service?"
echo "$ROUTES" | grep -E "Path|predicate" | head -20

# ─────────────────────────────────────────────────────────────
# 3. EUREKA — ¿pago-service está registrado?
# ─────────────────────────────────────────────────────────────
title "3. Eureka → ¿pago-service registrado?"

EUREKA_HEALTH=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8761/actuator/health 2>/dev/null)
if [ "$EUREKA_HEALTH" == "200" ]; then
  ok "Eureka health OK"
else
  warn "Eureka actuator en :8761 respondió HTTP $EUREKA_HEALTH (puede ser normal si no expone actuator)"
fi

PAGO_REG=$(curl -s -H "Accept: application/json" http://localhost:8761/eureka/apps/PAGO-SERVICE)
if echo "$PAGO_REG" | grep -qi "PAGO-SERVICE"; then
  ok "pago-service REGISTRADO en Eureka"
  # Extraer IP y puerto del registro Eureka
  IP=$(echo "$PAGO_REG" | grep -oP '"ipAddr"\s*:\s*"\K[^"]+' | head -1)
  PORT=$(echo "$PAGO_REG" | grep -oP '"port[^}]+"\$\{port\}"?\s*:\s*\K[0-9]+' | head -1)
  STATUS=$(echo "$PAGO_REG" | grep -oP '"status"\s*:\s*"\K[^"]+' | head -1)
  echo "    IP: ${IP:-desconocida}  Puerto: ${PORT:-desconocido}  Status: ${STATUS:-desconocido}"
else
  fail "pago-service NO está registrado en Eureka"
  warn "Verificar: docker logs pago-service --tail 50 | grep -i eureka"
fi

echo ""
echo "  Todos los servicios registrados en Eureka:"
curl -s -H "Accept: application/json" http://localhost:8761/eureka/apps | \
  grep -oP '"app"\s*:\s*"\K[^"]+' | sort | sed 's/^/    /'

# ─────────────────────────────────────────────────────────────
# 4. RED DOCKER — conectividad entre contenedores
# ─────────────────────────────────────────────────────────────
title "4. Red Docker → conectividad interna entre contenedores"

echo "  Containers corriendo:"
docker ps --format "  {{.Names}}\t{{.Status}}\t{{.Ports}}" 2>/dev/null | grep -v "^$" | head -15

echo ""
echo "  ¿api-gateway y pago-service en la misma red?"
GW_NET=$(docker inspect api-gateway --format '{{range $k,$v := .NetworkSettings.Networks}}{{$k}} {{end}}' 2>/dev/null)
PS_NET=$(docker inspect pago-service --format '{{range $k,$v := .NetworkSettings.Networks}}{{$k}} {{end}}' 2>/dev/null)
echo "  api-gateway redes: ${GW_NET:-'(no encontrado)'}"
echo "  pago-service redes: ${PS_NET:-'(no encontrado)'}"

if [ "$GW_NET" == "$PS_NET" ] && [ -n "$GW_NET" ]; then
  ok "Misma red Docker: $GW_NET"
else
  fail "Redes distintas o contenedor no encontrado"
fi

echo ""
echo "  Probando conectividad directa desde api-gateway → pago-service..."
# Intentar detectar el puerto de pago-service desde su registro Eureka
PAGO_PORT=$(curl -s -H "Accept: application/json" http://localhost:8761/eureka/apps/PAGO-SERVICE | \
  grep -oP '"port".*?"[^"]*"\s*:\s*\K[0-9]+' | head -1)
PAGO_PORT=${PAGO_PORT:-8085}

CONNECTIVITY=$(docker exec api-gateway wget -qO- --timeout=3 \
  "http://pago-service:${PAGO_PORT}/actuator/health" 2>/dev/null)
if echo "$CONNECTIVITY" | grep -q "UP"; then
  ok "Conectividad api-gateway → pago-service:${PAGO_PORT} OK"
elif echo "$CONNECTIVITY" | grep -q "status"; then
  warn "pago-service responde pero revisar status: $CONNECTIVITY"
else
  fail "api-gateway NO puede alcanzar pago-service:${PAGO_PORT}"
  warn "  Si pago-service usa port: 0 (random), buscar el puerto real en Eureka"
  warn "  docker exec api-gateway nslookup pago-service"
  docker exec api-gateway nslookup pago-service 2>/dev/null | head -5 | sed 's/^/  /'
fi

# ─────────────────────────────────────────────────────────────
# 5. PREFIJO DE RUTA — ¿coincide el predicado con @RequestMapping?
# ─────────────────────────────────────────────────────────────
title "5. Prefijo de ruta → ¿predicado vs @RequestMapping?"

echo "  Probando rutas posibles en el gateway:"

declare -A ROUTES_TO_TEST=(
  ["GET /pago-service/api/pagos (Opción B: predicado + StripPrefix=1)"]="http://localhost:8080/pago-service/api/pagos"
  ["GET /api/pagos (Opción A: predicado directo)"]="http://localhost:8080/api/pagos"
  ["GET /pagos (Sin prefijo /api)"]="http://localhost:8080/pagos"
)

for DESC in "${!ROUTES_TO_TEST[@]}"; do
  URL="${ROUTES_TO_TEST[$DESC]}"
  CODE=$(curl -s -o /dev/null -w "%{http_code}" "$URL")
  if [ "$CODE" == "200" ] || [ "$CODE" == "201" ]; then
    ok "HTTP $CODE → $DESC"
  elif [ "$CODE" == "404" ]; then
    fail "HTTP 404 → $DESC"
  else
    warn "HTTP $CODE → $DESC"
  fi
done

# ─────────────────────────────────────────────────────────────
# 6. LOGS EN TIEMPO REAL — capturar el 404 en vivo
# ─────────────────────────────────────────────────────────────
title "6. Capturar el 404 en logs del gateway (5 segundos)"

echo "  Haciendo la petición problemática..."
(sleep 1 && curl -s http://localhost:8080/api/pagos > /dev/null) &
docker logs api-gateway --since 5s -f 2>/dev/null &
LOGS_PID=$!
sleep 5
kill $LOGS_PID 2>/dev/null

# ─────────────────────────────────────────────────────────────
# CHECKLIST FINAL
# ─────────────────────────────────────────────────────────────
title "CHECKLIST FINAL"

CS_OK=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8888/actuator/health)
GW_OK=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/actuator/health)
PAGO_EUR=$(curl -s -H "Accept: application/json" http://localhost:8761/eureka/apps/PAGO-SERVICE | grep -c "PAGO-SERVICE")
ROUTES_LOADED=$(curl -s http://localhost:8080/actuator/gateway/routes | grep -c "pago")
LB_URI=$(curl -s http://localhost:8080/actuator/gateway/routes | grep -c "lb://")

[ "$CS_OK" == "200" ]       && ok "config-server responde en Docker"             || fail "config-server NO responde"
[ "$GW_OK" == "200" ]       && ok "gateway activo y responde"                    || fail "gateway NO activo"
[ "$ROUTES_LOADED" -gt "0" ] && ok "gateway recibió rutas del config-server"     || fail "gateway SIN rutas de pago-service"
[ "$PAGO_EUR" -gt "0" ]     && ok "pago-service visible en Eureka"               || fail "pago-service NO en Eureka"
[ "$LB_URI" -gt "0" ]       && ok "uri del gateway usa lb:// (correcto)"         || fail "uri NO usa lb:// (no hay load balancing)"
[ "$GW_NET" == "$PS_NET" ] && [ -n "$GW_NET" ] \
                            && ok "gateway y pago-service en la misma Docker network" \
                            || fail "gateway y pago-service en DISTINTAS redes"

echo ""
echo -e "  ${BOLD}CAUSA MÁS PROBABLE DETECTADA:${NC}"
echo -e "  ${YELLOW}El controller usa @RequestMapping(\"/api/pagos\")${NC}"
echo -e "  ${YELLOW}El gateway tiene Path=/pago-service/**  +  StripPrefix=1${NC}"
echo -e "  ${YELLOW}→ La URL correcta es: http://localhost:8080/pago-service/api/pagos${NC}"
echo -e "  ${YELLOW}→ O cambiar el predicado del gateway a: Path=/api/pagos/**${NC}"
echo ""
