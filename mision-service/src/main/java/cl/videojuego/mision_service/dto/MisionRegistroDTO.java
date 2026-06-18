package cl.videojuego.mision_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// DTO usado para crear o actualizar una misión
@Schema(description = "Datos requeridos para crear o actualizar una misión")
@Data
public class MisionRegistroDTO {

    @Schema(description = "Nombre de la misión", example = "Rescate en el bosque oscuro")
    @NotBlank(message = "El nombre de la misión es obligatorio")
    private String nombreMision;

    @Schema(description = "Descripción detallada de la misión", example = "Rescata al aldeano atrapado en el bosque")
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @Schema(description = "Puntos de experiencia otorgados al completar la misión", example = "500")
    @NotNull(message = "La recompensa de experiencia es obligatoria")
    private Integer recompensaExperiencia;

    @Schema(description = "Monedas otorgadas al completar la misión", example = "100")
    @NotNull(message = "La recompensa de monedas es obligatoria")
    private Integer recompensaMonedas;

    @Schema(description = "Nivel mínimo requerido para aceptar la misión", example = "5")
    @NotNull(message = "El nivel mínimo es obligatorio")
    private Integer nivelMinimo;

    @Schema(description = "ID del tipo de misión", example = "1")
    @NotNull(message = "El tipo de misión es obligatorio")
    private Long idTipoMision;

    @Schema(description = "ID del estado de la misión", example = "1")
    @NotNull(message = "El estado de misión es obligatorio")
    private Long idEstadoMision;
}