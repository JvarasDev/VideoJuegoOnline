package cl.videojuego.usuario_service.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * DTO de respuesta con los datos públicos de un usuario.
 * Usa Java Record para inmutabilidad y eliminación de boilerplate.
 */
@JsonPropertyOrder({"idUsuario", "nombre", "apellido", "correo", "nivelCuenta", "nombreRol", "nombreEstado"})
public record UsuarioDTO(
        Long idUsuario,
        String nombre,
        String apellido,
        String correo,
        Integer nivelCuenta,
        String nombreRol,
        String nombreEstado
) {}
