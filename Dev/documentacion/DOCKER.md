# Docker Configuration Guide - Plataforma de Videojuegos Online

## Overview
This document describes the Docker setup for the microservices architecture.

## Architecture

### Multi-Stage Build Pattern
Each microservice uses a **multi-stage Dockerfile** with two stages:

#### Stage 1: Builder
- **Base Image**: `eclipse-temurin:21-jdk-alpine`
- **Purpose**: Compile the application using Maven
- **Optimizations**:
  - Copy Maven wrapper and `pom.xml` first to leverage Docker layer caching
  - Pre-download dependencies separately before copying source code
  - This ensures dependency downloads are cached and not re-executed on source changes
- **Output**: Compiled JAR file

#### Stage 2: Runtime
- **Base Image**: `eclipse-temurin:21-jre-alpine` (JRE only, not JDK)
- **Purpose**: Run the compiled application
- **Security Features**:
  - Non-root user (`appuser`) for security
  - Proper file ownership
- **Optimizations**:
  - Only includes JRE, not JDK or build tools
  - Result: ~120 MB image size (vs ~400 MB with JDK)
- **Health Checks**: Spring Boot Actuator integration
- **JVM Settings**:
  - `UseContainerSupport`: Enables container-aware memory settings
  - `MaxRAMPercentage=75.0`: Uses 75% of container memory
  - `java.security.egd=file:/dev/./urandom`: Faster random number generation

## Services Configuration

### Infrastructure Services
1. **config-server** (Port 8888)
   - Spring Cloud Config Server
   - Centralized configuration management
   - HEALTHCHECK: `/actuator/health`

2. **eureka-server** (Port 8761)
   - Netflix Eureka Service Registry
   - Service discovery and registration
   - HEALTHCHECK: `/actuator/health`

### API Layer
3. **api-gateway** (Port 8080)
   - Spring Cloud Gateway
   - Route management and load balancing
   - HEALTHCHECK: `/actuator/health`

### Domain Microservices
4. **usuario-service** - User management
5. **personaje-service** - Character management
6. **arma-service** - Weapon management
7. **tienda-service** - Shop/Store management
8. **pago-service** - Payment processing
9. **inventario-service** - Inventory management
10. **mision-service** - Mission/Quest management
11. **ranking-service** - Leaderboard/Ranking
12. **combate-service** - Combat/Battle management

All domain services:
- Use Port: 8080 (mapped internally)
- Connect to MySQL database
- Register with Eureka
- Get config from Config Server
- HEALTHCHECK: `/actuator/health`

## Building and Running

### Build All Services
```bash
docker-compose build
```

### Build Specific Service
```bash
docker-compose build config-server
docker-compose build usuario-service
```

### Run All Services
```bash
docker-compose up
```

### Run Services in Detached Mode
```bash
docker-compose up -d
```

### View Logs
```bash
docker-compose logs -f
docker-compose logs -f config-server
```

### Stop Services
```bash
docker-compose down
```

### Remove Everything (including volumes)
```bash
docker-compose down -v
```

## Dockerfile Structure

Each microservice has an identical Dockerfile structure with service-specific labels:

```dockerfile
# Stage 1: Build
FROM eclipse-temurin:21-jdk-alpine AS builder
- Copies build files (.mvn/, mvnw, pom.xml)
- Pre-downloads dependencies
- Compiles source code

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine
- Copies only the JAR from builder
- Creates non-root user
- Exposes service port
- Configures health check
- Sets JVM options for containers
```

## Network and Communication

### Internal Network: `videojuegos-net`
- All services communicate through this bridge network
- Services can reach each other by container name (DNS resolution)
- Example: `http://config-server:8888/config/...`

### External Access
- Port mappings defined in `docker-compose.yml`:
  - Config Server: `8888:8888`
  - Eureka Server: `8761:8761`
  - API Gateway: `8080:8080`
  - Other services: Not directly accessible (only through API Gateway)

## Database

### MySQL Service
- Container Name: `mysql`
- Internal Port: `3306`
- External Port: `3307` (avoid conflict with local MySQL)
- Database: Multiple databases (one per service)
  - `usuario_db`, `personaje_db`, `arma_db`, etc.
- Volume: `mysql-data` (persistent storage)
- Initialization Script: `Script/crear_bases_datos.sql`

## Health Checks

All services implement health checks using Spring Boot Actuator:

### Health Check Configuration
```
--interval=30s    : Check every 30 seconds
--timeout=10s     : Wait max 10 seconds for response
--start-period=60s: Wait 60 seconds before first check
--retries=3       : Mark unhealthy after 3 failures
```

### Check Endpoint
- Spring Actuator endpoint: `/actuator/health`
- Returns JSON: `{"status":"UP"}`

### Usage
- Docker Compose uses health checks for service dependencies
- Example: `eureka-server` waits for `config-server` to be healthy

## Optimization Tips

### Reduce Build Time
1. **Layer Caching**: Project dependency downloads are cached in a separate layer
2. **Only change source code**: Dependencies won't be re-downloaded if only source changes
3. **Parallel builds**: Build multiple services simultaneously:
   ```bash
   docker-compose build --parallel
   ```

### Reduce Image Size
1. **Alpine Linux**: `eclipse-temurin:21-jre-alpine` is ~120 MB vs 400+ MB with full JDK
2. **Multi-stage builds**: Only runtime layer is kept; build layer is discarded
3. **No unnecessary files**: `.dockerignore` excludes build artifacts

### Monitor Image Sizes
```bash
docker images | grep videojuego
```

### Example Output
```
config-server      latest    abcd1234    210MB
eureka-server      latest    efgh5678    210MB
api-gateway        latest    ijkl9012    210MB
usuario-service    latest    mnop3456    210MB
```

## Troubleshooting

### Service Won't Start
```bash
# Check logs
docker-compose logs config-server

# Check specific error
docker-compose logs config-server | grep ERROR
```

### Health Check Failing
```bash
# Check if service is responding
docker exec config-server wget -qO- http://localhost:8888/actuator/health

# Check internal connectivity
docker exec config-server curl -s http://eureka-server:8761/actuator/health
```

### Database Connection Issues
```bash
# Check MySQL is running
docker-compose logs mysql

# Test MySQL connection from service
docker exec usuario-service mysql -h mysql -u videojuego -p videojuego usuario_db -e "SHOW TABLES;"
```

### Rebuild Without Cache
```bash
docker-compose build --no-cache
```

## Security Best Practices Implemented

1. **Non-root User**: Applications run as `appuser` (not root)
2. **Minimal Base Image**: Alpine Linux reduces attack surface
3. **Read-only Filesystem** (optional): Can be added to `docker-compose.yml`
4. **Resource Limits** (optional): Add to prevent resource exhaustion:
   ```yaml
   resources:
     limits:
       cpus: '0.5'
       memory: 512M
   ```

## Environment Variables

Services accept environment variables for configuration:

### Example (in docker-compose.yml)
```yaml
environment:
  - SPRING_PROFILES_ACTIVE=docker
  - DB_NAME=usuario_db
```

### Common Variables
- `SPRING_PROFILES_ACTIVE=docker`: Use Docker profile
- `DB_NAME`: Database name for service
- `JAVA_OPTS`: Additional JVM options

## Dockerfile Location

All Dockerfiles are located in their respective service directories:
- `/config-server/Dockerfile`
- `/eureka-server/Dockerfile`
- `/api-gateway/Dockerfile`
- `/usuario-service/Dockerfile`
- `/personaje-service/Dockerfile`
- `/arma-service/Dockerfile`
- `/tienda-service/Dockerfile`
- `/pago-service/Dockerfile`
- `/inventario-service/Dockerfile`
- `/mision-service/Dockerfile`
- `/ranking-service/Dockerfile`
- `/combate-service/Dockerfile`

## Centralized Dockerfile (Template)

A centralized template is maintained at:
- `/Dev/dependecy-docker/Dockerfile`

This serves as a reference. Individual service Dockerfiles are based on this template but include service-specific labels (e.g., description, port).

## Future Enhancements

1. **Image Registry**: Push to Docker Hub or private registry
2. **Kubernetes Deployment**: Convert docker-compose to Kubernetes manifests
3. **GitOps**: Automate builds and deployments
4. **Performance Tuning**: Profile and optimize each service individually
5. **Security Scanning**: Scan images for vulnerabilities (Trivy, etc.)
6. **Multi-architecture Builds**: Support ARM64 for development on Apple Silicon

## Contributing

When adding a new microservice:
1. Create service directory: `/new-service/`
2. Include: `pom.xml`, `.mvn/` directory, `mvnw`, `src/` directory
3. Create `Dockerfile` based on existing template
4. Add service entry to `docker-compose.yml`
5. Update this documentation

---

**Last Updated**: 2026-06-13
**Maintainer**: cl.videojuego
