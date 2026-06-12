package cl.videojuego.usuario_service.dto;

/**
 * DTO de respuesta tras una autenticación exitosa.
 */
public record AuthResponseDTO(
        String mensaje,
        String token,
        UsuarioDTO usuario
) {}
