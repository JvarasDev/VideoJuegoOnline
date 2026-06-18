package cl.videojuego.personaje_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "Datos requeridos para crear o actualizar un personaje")
@Data
public class PersonajeRegistroDTO {

    @Schema(description = "Nombre del personaje", example = "Kael el Guerrero")
    @NotBlank(message = "El nombre del personaje es obligatorio")
    private String nombre;

    @Schema(description = "ID del usuario propietario del personaje", example = "1")
    @NotNull(message = "El usuario es obligatorio")
    private Long idUsuario;

    @Schema(description = "ID de la clase del personaje", example = "2")
    @NotNull(message = "La clase del personaje es obligatoria")
    private Long idClasePersonaje;

    @Schema(description = "ID del estado del personaje", example = "1")
    @NotNull(message = "El estado del personaje es obligatorio")
    private Long idEstadoPersonaje;
}
