package cl.videojuego.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * API Gateway — punto de entrada único para todos los microservicios.
 * Gestiona el enrutamiento dinámico vía Spring Cloud Gateway (WebFlux)
 * y el descubrimiento de servicios a través de Eureka.
 */
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}

