---
### 🎯 Implementar pruebas unitarias base (JUnit/Mockito)

**Lista sugerida:** Backlog
**Etiqueta sugerida:** 🔴 Crítico
**IE relacionado:** IE 3.1.1, IE 3.1.2, IE 3.1.3
**Ponderación en juego:** 28% (8% grupal + 20% individual)

**Descripción:**
No existen pruebas unitarias en el proyecto, solo las clases generadas vacías. Esto genera un 0% automático en todos los ítems de testing en la rúbrica. Se debe implementar cobertura mínima en las clases de Servicio.

**Checklist:**
- [ ] Crear tests unitarios para `UsuarioService` usando `@ExtendWith(MockitoExtension.class)`.
- [ ] Crear tests para `ProductoTiendaService` (asegurarse de simular Feign con mocks).
- [ ] Replicar estructura Given-When-Then en los demás servicios.
- [ ] Ejecutar con cobertura en el IDE y alcanzar un mínimo del 80%.

**Responsable sugerido:** Por asignar
**Tiempo estimado:** 4 horas
---

---
### 🎯 Configurar Swagger/OpenAPI en todos los servicios

**Lista sugerida:** Backlog
**Etiqueta sugerida:** 🟡 Mejora
**IE relacionado:** IE 3.2.1, IE 3.2.2
**Ponderación en juego:** 9% (4% grupal + 5% individual)

**Descripción:**
La dependencia de `springdoc-openapi` solo está presente en Tienda y Pago. Falta en los otros 7 microservicios de dominio. Sin esto, la documentación API es evaluada como "incompleta".

**Checklist:**
- [ ] Copiar `<dependency>` de `springdoc` a los `pom.xml` faltantes.
- [ ] Levantar cada servicio y verificar acceso a `http://localhost:[puerto]/swagger-ui.html`.
- [ ] Revisar que los controladores expongan correctamente los endpoints.

**Responsable sugerido:** Por asignar
**Tiempo estimado:** 1 hora
---

---
### 🎯 Crear tablero en Trello y README.md del proyecto

**Lista sugerida:** Backlog
**Etiqueta sugerida:** 🟡 Mejora
**IE relacionado:** IE 2.5.1, IE 2.5.2
**Ponderación en juego:** 5% (grupal)

**Descripción:**
Falta evidencia formal de planificación ágil (Trello) y un README que describa el dominio, listado de servicios y los autores. Esto impacta directo en la evaluación del repositorio.

**Checklist:**
- [ ] Crear tablero en Trello, dividir en 'Backlog', 'En Progreso', 'Listo'. Agregar las tareas actuales.
- [ ] Crear `README.md` en la raíz del proyecto con: Nombres, microservicios, URLs de Swagger y el enlace público al Trello.
- [ ] Realizar un commit descriptivo: `docs: agregar README y evidencia de planificacion en Trello`.

**Responsable sugerido:** Juan / Elizabeth
**Tiempo estimado:** 1 hora
---

---
### 🎯 Configurar despliegue secundario (Render/Railway)

**Lista sugerida:** Backlog
**Etiqueta sugerida:** 🟡 Mejora
**IE relacionado:** IE 3.3.1
**Ponderación en juego:** 2% (Diferencia para llegar al 100% grupal)

**Descripción:**
Docker funciona excelente, pero la rúbrica exige explícitamente DOS herramientas de despliegue operativas para alcanzar la nota máxima. Hay que preparar la configuración en la nube.

**Checklist:**
- [ ] Elegir plataforma de nube (ej. Render o Railway).
- [ ] Agregar el archivo de configuración en la raíz (ej. `railway.json` o `render.yaml`).
- [ ] Mapear las variables de entorno necesarias (`DB_NAME`, `SPRING_PROFILES_ACTIVE`).

**Responsable sugerido:** Por asignar
**Tiempo estimado:** 2 horas
---

---
### 🎯 Preparación de la defensa en vivo (Juan)

**Lista sugerida:** Listo para defensa
**Etiqueta sugerida:** 🟢 Defensa-individual
**IE relacionado:** Todos los IE de la Dimensión Defensa
**Ponderación en juego:** 60% (individual)

**Descripción:**
Práctica individual obligatoria para la defensa oral técnica ante el profesor. Se debe asegurar la fluidez al codificar y explicar la arquitectura del sistema sin apoyo de apuntes o IA.

**Checklist:**
- [ ] Practicar escritura de un test con Mockito en 5 minutos (es IE 3.1.3 y vale 13%).
- [ ] Identificar 3-4 commits propios en Github y comprender perfectamente qué código se modificó.
- [ ] Explicar el flujo del archivo `api-gateway.yml` y cómo lee la configuración dinámica de `config-server`.
- [ ] Practicar levantar el `docker-compose up -d --build` y probar endpoints desde Postman sin errores.

**Responsable sugerido:** Juan
**Tiempo estimado:** 2 horas
---
