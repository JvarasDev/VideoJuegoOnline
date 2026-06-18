package cl.videojuego.usuario_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para registrar o actualizar un usuario.
 * Las validaciones se aplican directamente en los componentes del record.
 */
@Schema(description = "Objeto de transferencia de datos para registrar un usuario")
public record UsuarioRegistroDTO(

        @Schema(description = "Nombre del usuario", example = "Juan")
        @NotBlank(message = "El nombre es obligatorio") @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres") String nombre,

        @Schema(description = "Apellido del usuario", example = "Perez")
        @NotBlank(message = "El apellido es obligatorio") @Size(min = 1, max = 50, message = "El apellido debe tener entre 1 y 50 caracteres") String apellido,

        @Schema(description = "Correo electrónico", example = "juan.perez@email.com")
        @NotBlank(message = "El correo es obligatorio") @Email(message = "El correo debe tener un formato válido") String correo,

        @Schema(description = "Contraseña segura", example = "MiPassword123")
        @NotBlank(message = "La contraseña es obligatoria") @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres") String contrasena,

        @Schema(description = "ID del Rol", example = "1")
        @NotNull(message = "El rol es obligatorio") Long idRol,

        @Schema(description = "ID del Estado", example = "1")
        @NotNull(message = "El estado del usuario es obligatorio") Long idEstadoUsuario) {
}
