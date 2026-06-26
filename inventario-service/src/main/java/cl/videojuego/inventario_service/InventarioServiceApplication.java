package cl.videojuego.inventario_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * @author Juan Varas
 */
@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(
    info = @Info(
        title = "API de Inventario Service",
        version = "1.0.0",
        description = "Microservicio para la gestión de inventarios y sus ítems en el Videojuego Online",
        contact = @Contact(name = "Juan Varas")
    )
)
public class InventarioServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventarioServiceApplication.class, args);
	}
}