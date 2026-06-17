# Docker Quick Reference - VideoJuegoOnline

## Essential Commands

### Build and Start
```bash
# Build all services
docker-compose build

# Start all services
docker-compose up

# Start in background
docker-compose up -d

# Build and start specific service
docker-compose up --build config-server
```

### View Status and Logs
```bash
# View running containers
docker-compose ps

# View logs for all services
docker-compose logs

# View logs for specific service
docker-compose logs -f config-server

# View last 100 lines and follow
docker-compose logs --tail=100 -f usuario-service

# View logs for multiple services
docker-compose logs -f config-server eureka-server api-gateway
```

### Stop and Clean
```bash
# Stop all services (keep containers)
docker-compose stop

# Stop and remove containers
docker-compose down

# Stop, remove containers, and delete volumes
docker-compose down -v

# Stop specific service
docker-compose stop usuario-service
```

### Rebuild and Restart
```bash
# Rebuild all services (no cache)
docker-compose build --no-cache

# Rebuild and restart
docker-compose up --build

# Rebuild specific service
docker-compose build --no-cache usuario-service
```

### Execute Commands in Containers
```bash
# Run command in running container
docker-compose exec usuario-service ls -la

# Access MySQL database
docker-compose exec mysql mysql -u videojuego -p videojuego usuario_db

# Check service health
docker-compose exec config-server wget -qO- http://localhost:8888/actuator/health

# View Java processes
docker-compose exec usuario-service ps aux | grep java
```

## Service Access

### Local Ports
- **Config Server**: http://localhost:8888
- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8080
- **MySQL**: localhost:3307 (root/root)

### Service-to-Service Communication (internal)
```
http://config-server:8888
http://eureka-server:8761
http://api-gateway:8080
http://usuario-service:8080 (internal only)
```

## Troubleshooting

### Service Won't Start
```bash
# Check logs
docker-compose logs config-server

# Rebuild without cache
docker-compose build --no-cache config-server

# Start with verbose output
docker-compose up --verbose
```

### Health Check Failing
```bash
# Check service health directly
docker-compose exec config-server curl -v http://localhost:8888/actuator/health

# Check database connectivity
docker-compose exec usuario-service mysql -h mysql -u videojuego -pvideojuego
```

### Port Already in Use
```bash
# Find process using port
lsof -i :8080

# Change port in docker-compose.yml
# ports:
#   - "9080:8080"
```

### Database Issues
```bash
# Check MySQL logs
docker-compose logs mysql

# Check database exists
docker-compose exec mysql mysql -uroot -proot -e "SHOW DATABASES;"

# Recreate MySQL with fresh data
docker-compose down -v
docker-compose up -d mysql
```

### Build Issues
```bash
# Clear Docker cache
docker system prune -a

# Rebuild with maximum verbosity
docker-compose build --no-cache --verbose

# Check Dockerfile syntax
docker build --dry-run .
```

## Performance Tips

### Faster Rebuilds
- Only source code changed? → Dependencies cached ✓
- Changes to pom.xml? → All layers rebuilt

### Smaller Image Sizes
- Current: ~120 MB per service (JRE Alpine)
- Space saved vs full JDK: ~3.3x smaller

### Monitor Resources
```bash
# View memory usage
docker stats

# View detailed container info
docker-compose ps -a

# Check image sizes
docker images | grep videojuego
```

## File Structure

```
VideoJuegoOnline/
├── docker-compose.yml          ← Orchestration config
├── .dockerignore              ← Build optimization
├── DOCKER.md                  ← Detailed guide
├── DOCKER_QUICKREF.md         ← This file
├── config-server/
│   └── Dockerfile             ← Service container def
├── eureka-server/
│   └── Dockerfile
├── api-gateway/
│   └── Dockerfile
└── [9 more microservices]/
    └── Dockerfile
```

## Environment Variables

Services accept configuration via environment variables in `docker-compose.yml`:

```yaml
environment:
  - SPRING_PROFILES_ACTIVE=docker    # Use docker profile
  - DB_NAME=usuario_db               # Database name
  - JAVA_OPTS=-Xmx512m               # JVM heap size
```

## Network Details

### Internal Network: `videojuegos-net`
- Bridge network connects all services
- Services communicate by container name (DNS)
- Isolated from other docker networks

### Port Mapping Format
```yaml
ports:
  - "HOST_PORT:CONTAINER_PORT"
  - "8888:8888"  # Config Server
```

## Health Checks

All services check health every 30 seconds via:
```
GET /actuator/health
Expected: {"status":"UP"}
```

## Development Workflow

### Iterative Development
```bash
# 1. Make code changes
nano usuario-service/src/main/java/...

# 2. Rebuild only affected service
docker-compose build usuario-service

# 3. Restart service
docker-compose up -d usuario-service

# 4. Check logs
docker-compose logs -f usuario-service
```

### Full Stack Testing
```bash
# 1. Build all
docker-compose build

# 2. Start fresh
docker-compose down -v
docker-compose up -d

# 3. Verify all healthy
docker-compose ps

# 4. Run tests
docker-compose exec api-gateway curl http://api-gateway:8080/actuator/health
```

## Production Deployment

### Push to Registry
```bash
# Tag images
docker tag videosjuegosonline-config-server:latest myregistry/config-server:v1.0

# Push
docker push myregistry/config-server:v1.0
```

### Kubernetes Migration
- Use `kompose` to convert docker-compose to K8s manifests
- Example: `kompose convert -f docker-compose.yml`

### CI/CD Integration
- Build images in pipeline
- Run automated tests
- Push to registry
- Deploy to target environment

## Useful Aliases

Add to your `.bashrc` or `.zshrc`:

```bash
alias dcu='docker-compose up'
alias dcd='docker-compose down'
alias dcb='docker-compose build'
alias dcl='docker-compose logs -f'
alias dcp='docker-compose ps'
alias dce='docker-compose exec'
```

## Resources

- See `DOCKER.md` for detailed documentation
- Docker Compose docs: https://docs.docker.com/compose/
- Spring Boot in Docker: https://spring.io/guides/gs/spring-boot-docker/
- Eclipse Temurin: https://projects.eclipse.org/projects/adoptium.temurin
