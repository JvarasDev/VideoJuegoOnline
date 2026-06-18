package cl.videojuego.usuario_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para la solicitud de inicio de sesión.
 */
@Schema(description = "Objeto de transferencia de datos para el login")
public record LoginRequestDTO(

        @Schema(description = "Correo electrónico del usuario", example = "juan.perez@email.com")
        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo debe tener un formato válido")
        String correo,

        @Schema(description = "Contraseña del usuario", example = "MiPassword123")
        @NotBlank(message = "La contraseña es obligatoria")
        String contrasena
) {}
