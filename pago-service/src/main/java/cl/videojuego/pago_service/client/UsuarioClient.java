package cl.videojuego.pago_service.client;

import cl.videojuego.pago_service.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuario-service")
public interface UsuarioClient {

    @GetMapping("/api/usuarios/{idUsuario}")
    UsuarioDTO buscarUsuarioPorId(@PathVariable Long idUsuario);
}