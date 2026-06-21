package cl.videojuego.pago_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;



@OpenAPIDefinition(
		info = @Info(
				title = "API VideoJuegos - Pago Service",
				description = "Microservicio encargado de gestionar los pagos realizados dentro del sistema de videojuegos online.Permite registrar, consultar, actualizar y eliminar pagos asociados a compras de productos de la tienda virtual",
				version = "1.0.0",
				contact = @Contact(
					name = "Elizabeth Reyes ",
					email = "lizz123@gmail.com"
				)
		)
)


@SpringBootApplication
@EnableFeignClients
public class PagoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PagoServiceApplication.class, args);
	}
}