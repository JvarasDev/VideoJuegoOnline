package cl.videojuego.inventario_service.client;

import cl.videojuego.inventario_service.dto.ArmaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "api-gateway", contextId = "armaClient")
public interface ArmaClient {

    @GetMapping("/api/armas/{idArma}")
    ArmaDTO buscarArmaPorId(@PathVariable Long idArma);
}