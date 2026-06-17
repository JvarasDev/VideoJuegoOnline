# Auditoría HitboxKing vs. Rúbrica DSY1103 — Evaluación Parcial 3

**Fecha de auditoría:** 17 de Junio de 2026
**Repositorio analizado:** `c:\Users\juanm\Desktop\fullstack_git\VideoJuegoOnline`
**Microservicios detectados:** 12 / 10 mínimo requerido (9 de dominio, 3 de infraestructura)

## 1. Resumen ejecutivo
El proyecto presenta una excelente base arquitectónica: el patrón CSR, API Gateway, Spring Cloud Config y Docker Compose están muy bien configurados. Sin embargo, **existe un riesgo crítico (0%) en la cobertura de pruebas unitarias**, las cuales no han sido implementadas, lo que impactará negativamente la nota grupal e impedirá la defensa individual. Además falta Swagger en la mayoría de servicios y configurar Trello/README. La nota grupal estimada actual ronda el 20.7% sobre 40%.

## 2. Estado por microservicio
| Microservicio | Puerto | CSR correcto | Tests | Swagger | Comunicación REST | Estado general |
|---|---|---|---|---|---|---|
| usuario-service | 8081 | ✅ Sí | 🔴 No | 🔴 No | ✅ Sí | 🟡 Faltan tests y Swagger |
| personaje-service | 8082 | ✅ Sí | 🔴 No | 🔴 No | ✅ Sí | 🟡 Faltan tests y Swagger |
| arma-service | 8083 | ✅ Sí | 🔴 No | 🔴 No | ✅ Sí | 🟡 Faltan tests y Swagger |
| tienda-service | 8084 | ✅ Sí | 🔴 No | 🟡 POM | ✅ Sí (Feign) | 🟡 Faltan tests |
| pago-service | 8085 | ✅ Sí | 🔴 No | 🟡 POM | ✅ Sí (Feign) | 🟡 Faltan tests |
| inventario-service | 8086 | ✅ Sí | 🔴 No | 🔴 No | ✅ Sí (Feign) | 🟡 Faltan tests y Swagger |
| mision-service | 8087 | ✅ Sí | 🔴 No | 🔴 No | ✅ Sí | 🟡 Faltan tests y Swagger |
| ranking-service | 8088 | ✅ Sí | 🔴 No | 🔴 No | ✅ Sí (Feign) | 🟡 Faltan tests y Swagger |
| combate-service | 8089 | ✅ Sí | 🔴 No | 🔴 No | ✅ Sí (Feign) | 🟡 Faltan tests y Swagger |

## 3. Tabla de cumplimiento por Indicador de Evaluación

### Dimensión Entrega de Encargo (40%)
| IE | Descripción breve | Ponderación | Nivel detectado | % logro | Evidencia / Justificación |
|---|---|---|---|---|---|
| 1.2.1 | Patrón CSR | 2% | 100% | 2.0% | Paquetes separados correctamente, controladores delegan en servicios (ej. `UsuarioController`). |
| 2.2.1 | Reglas de negocio | 3% | 60% | 1.8% | Lógica presente, pero la falta de tests dificulta verificar cobertura total de casos críticos. |
| 2.4.1 | Comunicación REST | 3% | 60% | 1.8% | Se usa FeignClient y ExceptionHandlers. Fallbacks aplicados manualmente; falta circuito real (Resilience4j) para el 100%. |
| 2.5.1 | Commits en GitHub | 3% | 30% | 0.9% | Mensajes escasos y poco técnicos ("creacion db", "Add files via upload"). Faltan descripciones. |
| 2.5.2 | Organización Trello | 2% | 0% | 0.0% | 🔴 No se detectó configuración ni enlace a Trello (o similar) en el repositorio. |
| 3.1.1 | Pruebas Unitarias | 8% | 0% | 0.0% | 🔴 Solo existen tests generados vacíos (`contextLoads()`). No hay pruebas reales de lógica. |
| 3.2.1 | Swagger/OpenAPI | 4% | 30% | 1.2% | Dependencia solo configurada en `tienda-service` y `pago-service`. Incompleta en el resto. |
| 3.3.1 | Despliegue 2 entornos| 5% | 60% | 3.0% | Docker Compose excelente, pero falta evidencia de una segunda herramienta (Render/Railway). |
| 3.3.2 | API Gateway | 5% | 100% | 5.0% | Configurado robustamente con WebFlux, timeouts globales y enrutamiento hacia Eureka. |
| 3.3.3 | Interoperabilidad | 2% | 100% | 2.0% | Rutas precisas (`lb://`) y CORS configurado correctamente a nivel global. |
| 3.3.4 | Configuración YAML | 3% | 100% | 3.0% | `config-server` centraliza de manera excelente todos los perfiles de todos los microservicios. |

### Dimensión Defensa (60%)
| IE | Descripción breve | Ponderación | ¿Código permite defenderlo bien? | Riesgo | Qué debe preparar el estudiante |
|---|---|---|---|---|---|
| 2.2.2 | Explica código | 5% | Sí | Bajo | Explicar el flujo desde Controller hasta BD. |
| 2.4.2 | Explica REST | 5% | Sí | Bajo | Explicar el uso de FeignClient y cómo manejaron los fallbacks manualmente. |
| 2.5.3 | Explica su aporte | 5% | Parcial | Medio | Memorizar qué commits específicos hizo y justificarlos (evitar dudas en la autoría). |
| 3.1.2 | Explica pruebas | 7% | 🔴 No | Crítico | **Debe crear tests primero** para poder tener algo que explicar. |
| 3.1.3 | Crea prueba en vivo| 13% | 🔴 No | Crítico | Al no haber base de Mockito/JUnit, será casi imposible improvisar la prueba en la defensa. |
| 3.2.2 | Explica Swagger | 5% | Parcial | Alto | Debe completar la instalación de Swagger para poder mostrar la UI en vivo. |
| 3.3.5 | Explica YAML | 4% | Sí | Bajo | Explicar cómo el Gateway y los servicios leen de `config-server`. |
| 3.3.6 | Explica despliegue | 6% | Sí | Medio | Explicar el Docker Compose, las redes (`videojuegos-net`) y los `healthchecks`. |
| 3.3.7 | Configura en vivo | 10% | Sí | Bajo | Levantar los contenedores limpios sin errores en su propia máquina. |

## 4. Nota estimada actual
- Entrega de Encargo (40%): **20.7%** acumulado.
- Riesgo en Defensa (60%): **MUY ALTO** (Pérdida inminente de 20% asociado a la dimensión de pruebas).
- **Estimado total si se entregara hoy:** **Insuficiente (Falta crítica de Pruebas Unitarias que impide evaluación).**

## 5. Brechas críticas (🔴 riesgo de 0 automático)
- **IE 3.1.1 (Pruebas Unitarias):** No existen tests desarrollados, lo cual impacta el 8% grupal y arrastra a 0% los IE 3.1.2 y 3.1.3 (20% individual).
- **IE 2.5.2 (Gestión Trello):** No existe un tablero con roles y avance visible.

## 6. Brechas de mejora (🟡 desempeño incipiente/aceptable)
- **IE 3.2.1 (Swagger):** Faltante en 7 microservicios, debe añadirse al POM y verificar.
- **IE 3.3.1 (Despliegue):** Falta configurar un archivo para plataforma remota (`railway.json` o `render.yaml`) para asegurar el 100%.
- **IE 2.5.1 (Commits y README):** Falta un README principal descriptivo y commits más técnicos.

## 7. Fortalezas detectadas
- **Arquitectura y Configuración:** El uso de `config-server` como fuente centralizada está al nivel de un proyecto profesional. No lo toquen, está excelente.
- **Docker Compose:** El uso avanzado de `healthcheck` y secuencias de inicio condicionales (`condition: service_healthy`) está perfecto y asegura estabilidad.
- **API Gateway:** Global CORS y timeouts están configurados correctamente, previniendo cuellos de botella.

## 8. Plan de acción priorizado
1. **(Urgente - 4 hrs)** Implementar tests unitarios (JUnit 5 / Mockito) en los Services de cada microservicio para apuntar a un 80% de cobertura. **Sin esto, la defensa fracasará.**
2. **(Alta - 1 hr)** Agregar la dependencia de `springdoc-openapi` a los demás `pom.xml`.
3. **(Media - 2 hrs)** Escribir un `README.md` con nombres, rutas, y enlace a un tablero de Trello (crearlo y llenarlo retroactivamente).
4. **(Baja - 1 hr)** Agregar configuración básica para Railway o Render en un repositorio remoto para cumplir el segundo entorno.

## 9. Checklist de preparación para la defensa individual (Juan)
- [ ] Memorizar la estructura Given-When-Then y cómo usar `@Mock`, `@InjectMocks` y `when().thenReturn()` en Mockito.
- [ ] Practicar la escritura de un test unitario desde cero en menos de 5 minutos, sin consultar Google/ChatGPT (Requisito de IE 3.1.3).
- [ ] Comprender y poder explicar cómo funciona la configuración `lb://` en el Gateway interactuando con Eureka.
- [ ] Identificar exactamente qué líneas y commits hiciste tú, para defender el IE 2.5.3 sin dudar.
- [ ] Levantar el proyecto con `docker-compose up -d --build` en tu equipo, asegurando que puertos y variables locales funcionan a la primera.
