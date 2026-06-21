package cl.videojuego.usuario_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "API Videojuegos - Usuario Service",
				version = "1.0.0",
				description = "Microservicio encargado de la gestión de usuarios del sistema de videojuegos online. Permite registrar, consultar, actualizar y eliminar usuarios, además de administrar roles y estados de usuario.",
				contact = @Contact(
						name = "Juan Varas-Lizz",
						email = "bethreyesss@gmail.com"
				)
		)
)




@SpringBootApplication
public class UsuarioServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsuarioServiceApplication.class, args);
	}




}
