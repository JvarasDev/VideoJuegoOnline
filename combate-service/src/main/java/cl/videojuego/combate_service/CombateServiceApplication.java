package cl.videojuego.combate_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CombateServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CombateServiceApplication.class, args);
	}
}