package cl.videojuego.ranking_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@OpenAPIDefinition(
		info = @Info(
				title = "API Videojuegos - Ranking Service",
				version = "1.0.0",
				description = "Microservicio encargado de la gestión de rankings competitivos del videojuego. Permite registrar posiciones, consultar rankings por personaje, liga, temporada y obtener el Top 10 global.",
				contact = @Contact(
						name = "Elizabeth Reyes",
						email = "bethreyesss@gmail.com"
				)
		)
)
@SpringBootApplication
@EnableFeignClients
public class RankingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RankingServiceApplication.class, args);
	}
}