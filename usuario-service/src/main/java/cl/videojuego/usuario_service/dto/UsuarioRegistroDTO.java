package cl.videojuego.usuario_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para registrar o actualizar un usuario.
 * Las validaciones se aplican directamente en los componentes del record.
 */
public record UsuarioRegistroDTO(

                @NotBlank(message = "El nombre es obligatorio") @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres") String nombre,

                @NotBlank(message = "El apellido es obligatorio") @Size(min = 1, max = 50, message = "El apellido debe tener entre 1 y 50 caracteres") String apellido,

                @NotBlank(message = "El correo es obligatorio") @Email(message = "El correo debe tener un formato válido") String correo,

                @NotBlank(message = "La contraseña es obligatoria") @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres") String contrasena,

                @NotNull(message = "El rol es obligatorio") Long idRol,

                @NotNull(message = "El estado del usuario es obligatorio") Long idEstadoUsuario) {
}
