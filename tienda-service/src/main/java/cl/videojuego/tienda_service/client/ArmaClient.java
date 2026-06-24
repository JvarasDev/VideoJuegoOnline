package cl.videojuego.tienda_service.client;

import cl.videojuego.tienda_service.dto.ArmaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Cliente Feign para comunicarse con arma-service mediante Eureka
@FeignClient(name = "api-gateway", contextId = "armaClient")
public interface ArmaClient {

    // Llama a arma-service para buscar un arma por ID
    @GetMapping("/api/armas/{idArma}")
    ArmaDTO buscarArmaPorId(@PathVariable Long idArma);
}