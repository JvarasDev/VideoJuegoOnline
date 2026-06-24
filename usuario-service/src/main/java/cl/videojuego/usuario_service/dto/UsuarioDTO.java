package cl.videojuego.usuario_service.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO de respuesta con los datos públicos de un usuario.
 * Usa Java Record para inmutabilidad y eliminación de boilerplate.
 */
@Schema(description = "Objeto de transferencia de datos con la información pública y segura del usuario")
@JsonPropertyOrder({"idUsuario", "nombre", "apellido", "correo", "nivelCuenta", "nombreRol", "nombreEstado"})
public record UsuarioDTO(
        
        @Schema(description = "Identificador único del usuario", example = "1")
        Long idUsuario,
        
        @Schema(description = "Nombre(s) del usuario", example = "Juan")
        String nombre,
        
        @Schema(description = "Apellido(s) del usuario", example = "Pérez")
        String apellido,
        
        @Schema(description = "Correo electrónico asociado a la cuenta", example = "juan.perez@email.com")
        String correo,
        
        @Schema(description = "Nivel actual de la cuenta del usuario dentro del videojuego", example = "15")
        Integer nivelCuenta,
        
        @Schema(description = "Nombre descriptivo del rol asignado (ej. ADMIN, JUGADOR)", example = "JUGADOR")
        String nombreRol,
        
        @Schema(description = "Estado actual en el que se encuentra la cuenta (ej. ACTIVO, BANEADO)", example = "ACTIVO")
        String nombreEstado
) {}
