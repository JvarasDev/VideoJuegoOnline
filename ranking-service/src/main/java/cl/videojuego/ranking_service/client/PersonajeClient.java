package cl.videojuego.ranking_service.client;

import cl.videojuego.ranking_service.dto.PersonajeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "personaje-service")
public interface PersonajeClient {

    @GetMapping("/api/personajes/{idPersonaje}")
    PersonajeDTO buscarPersonajePorId(@PathVariable Long idPersonaje);
}