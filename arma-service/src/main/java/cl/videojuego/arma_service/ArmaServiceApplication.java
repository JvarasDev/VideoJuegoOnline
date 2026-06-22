package cl.videojuego.arma_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "API Videojuegos - Arma Service", version = "1.0.0", description = "Microservicio encargado de la gestión de armas del sistema de videojuegos online. Permite registrar, consultar y filtrar armas por tipo, rareza, nivel mínimo, nombre y precio.", contact = @Contact(name = "Elizabeth Reyes- Juan Varas ", email = "lIZ123@gmail.com")))
@SpringBootApplication
public class ArmaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArmaServiceApplication.class, args);
	}

}
