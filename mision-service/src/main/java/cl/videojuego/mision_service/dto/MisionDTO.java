package cl.videojuego.mision_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Datos detallados de una misión en el juego")
@Data
public class MisionDTO {

    @Schema(description = "Identificador único de la misión", example = "1")
    private Long idMision;

    @Schema(description = "Nombre de la misión", example = "Rescate en el bosque oscuro")
    private String nombreMision;

    @Schema(description = "Descripción detallada de la misión", example = "Rescata al aldeano atrapado en el bosque")
    private String descripcion;

    @Schema(description = "Puntos de experiencia otorgados al completar la misión", example = "500")
    private Integer recompensaExperiencia;

    @Schema(description = "Monedas otorgadas al completar la misión", example = "100")
    private Integer recompensaMonedas;

    @Schema(description = "Nivel mínimo requerido para aceptar la misión", example = "5")
    private Integer nivelMinimo;

    @Schema(description = "Tipo de misión", example = "Rescate")
    private String nombreTipo;

    @Schema(description = "Estado actual de la misión", example = "DISPONIBLE")
    private String nombreEstado;
}