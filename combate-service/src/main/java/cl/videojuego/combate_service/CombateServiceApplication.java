package cl.videojuego.combate_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Clase principal de la aplicación Combate Service.
 */
@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(info = @Info(title = "Combate Service API", version = "1.0", description = "API para gestionar los combates y su historial en el sistema de videojuegos", contact = @Contact(name = "Juan V.", url = "https://github.com/JvarasDev", email = "ju.varasc@duocuc.cl")))
public class CombateServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CombateServiceApplication.class, args);
	}
}