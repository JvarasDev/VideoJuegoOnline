package cl.videojuego.personaje_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;







@OpenAPIDefinition(
		info = @Info(
				title = "API Videojuegos - Personaje Service",
				version = "1.0.0",
				description = "Microservicio encargado de la gestión de personajes del sistema de videojuegos online. Permite registrar personajes, consultar personajes por usuario, nivel, clase y estado. Además valida usuarios mediante OpenFeign antes de crear un personaje.",
				contact = @Contact(
						name = "Elizabeth Reyes",
						email = "LizReyes123@gmail.com"
				)
		)
)
@SpringBootApplication
@EnableFeignClients
public class PersonajeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonajeServiceApplication.class, args);
	}
}