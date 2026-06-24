package cl.videojuego.tienda_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        Server gatewayServer = new Server();
        gatewayServer.setUrl("http://localhost:8080");
        gatewayServer.setDescription("API Gateway");

        return new OpenAPI()
                .info(new Info()
                    .title("API de Microservicio")
                    .version("1.0.0")
                    .contact(new Contact()
                        .name("Juan Varas, Elizabeth Reyes y Scarlett Riquelme")))
                .servers(List.of(gatewayServer));
    }
}


