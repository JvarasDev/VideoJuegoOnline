# Docker helpers — solucionar PKIX/SSL y builds Maven en Docker

Contenido
- `settings.xml.template` — plantilla de `~/.m2/settings.xml` para proxies/mirrors.
- `import-cert.Dockerfile.fragment` — fragmento para insertar en Dockerfile y agregar un certificado al cacerts de la JVM.
- `build-with-settings.ps1` — script PowerShell para copiar `settings.xml` y `corp-proxy.crt` a un servicio, ejecutar `docker build --network=host` y limpiar.

Flujo recomendado (rápido)
1. Si tienes un proxy corporativo o un Artifactory, rellena `settings.xml.template` y guárdalo como `settings.xml` junto al servicio que vas a construir (por ejemplo `api-gateway/settings.xml`) o úsalo desde el host en `~/.m2/settings.xml`.
2. Si la empresa hace inspección SSL, exporta el certificado CA/proxy (`corp-proxy.crt`) y colócalo en el repo (fuera del control de versiones idealmente) o en una ruta privada.
3. Ejecuta el script desde la raíz del repositorio (PowerShell):
```
.\Dev\docker_helpers\build-with-settings.ps1 -ServicePath .\api-gateway -SettingsPath .\Dev\docker_helpers\settings.xml.template -CertPath C:\path\to\corp-proxy.crt -ImageTag api-gateway:local
```
4. El script copia temporalmente `settings.xml` y `corp-proxy.crt` al directorio del servicio, ejecuta `docker build --network=host` y los remueve.

Notas y alternativas
- Si no puedes usar `--network=host`, importa el certificado manualmente en el JDK del host con `keytool -importcert ...` (ver fragmento en `import-cert.Dockerfile.fragment`).
- Otra alternativa temporal es compilar localmente con `./mvnw -U package -DskipTests` (host), y modificar el `Dockerfile` para que copie el JAR resultante en lugar de ejecutar Maven dentro de la imagen.

Seguridad
- No subas `corp-proxy.crt` ni `settings.xml` con credenciales a repositorios públicos.
