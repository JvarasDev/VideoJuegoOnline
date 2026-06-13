# =============================================================
# STAGE 1 — BUILD
# Compila el JAR usando Maven Wrapper. El .dockerignore excluye
# target/ para que Docker no copie artefactos previos.
# =============================================================
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Copiar wrapper primero para aprovechar la cache de capas de Docker:
# Si solo cambia el código fuente, las dependencias NO se re-descargan.
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Dar permisos de ejecución al wrapper (necesario en Linux/Alpine)
RUN chmod +x mvnw

# Pre-descargar dependencias (cacheadas en capas separadas)
RUN ./mvnw dependency:go-offline -B

# Copiar código fuente y construir el JAR ejecutable
COPY src ./src
RUN ./mvnw package -DskipTests -B

# =============================================================
# STAGE 2 — RUNTIME
# Imagen mínima JRE (sin JDK, sin Maven, sin código fuente).
# Resultado: imagen ~120 MB en lugar de ~400 MB.
# =============================================================
FROM eclipse-temurin:21-jre-alpine AS runtime

# Metadatos de la imagen
LABEL maintainer="cl.videojuego"
LABEL description="Pago Service - Plataforma de Videojuegos Online"

WORKDIR /app

# Usuario no-root por seguridad (buena práctica Docker)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copiar solo el JAR desde el stage de build
COPY --from=builder /app/target/*.jar app.jar

# Cambiar propietario del archivo al usuario no-root
RUN chown appuser:appgroup app.jar

USER appuser

# Puerto por defecto
EXPOSE 8080

# Health check básico vía Actuator
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD wget -qO- http://localhost:8080/actuator/health | grep -q '"status":"UP"' || exit 1

# Punto de entrada: variables de entorno JVM optimizadas para contenedores
ENTRYPOINT ["java", \
  "-XX:+UseContainerSupport", \
  "-XX:MaxRAMPercentage=75.0", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-jar", "app.jar"]
