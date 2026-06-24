package cl.videojuego.tienda_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;




@OpenAPIDefinition(
		info = @Info(
				title = "API Videojuegos - Tienda Service",
				version = "1.0.0",
				description = "Microservicio encargado de la gestión de productos de la tienda del videojuego. Permite registrar, consultar y filtrar productos por categoría, estado, precio, nombre y stock. Además puede validar información de armas mediante comunicación entre microservicios.",
				contact = @Contact(
						name = "Elizabeth Reyes",
						email = "liz123@gmail.com"
				)
		)
)
@SpringBootApplication
@EnableFeignClients
public class TiendaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiendaServiceApplication.class, args);
	}
}
