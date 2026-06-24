package cl.videojuego.mision_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@OpenAPIDefinition(
		info = @Info(
				title = "API Videojuegos - Mision Service",
				version = "1.0.0",
				description = "Microservicio encargado de la gestión de misiones del videojuego. Permite registrar, consultar y filtrar misiones según tipo, estado, nivel mínimo, nombre y recompensa.",
				contact = @Contact(
						name = "Juan Varas -lizz",
						email = "Juan123@gmail.com"
				)
		)
)
@SpringBootApplication
public class MisionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MisionServiceApplication.class, args);
	}

}
