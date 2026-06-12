package cl.videojuego.mision_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// DTO usado para crear o actualizar una misión
@Data
public class MisionRegistroDTO {

    @NotBlank(message = "El nombre de la misión es obligatorio")
    private String nombreMision;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "La recompensa de experiencia es obligatoria")
    private Integer recompensaExperiencia;

    @NotNull(message = "La recompensa de monedas es obligatoria")
    private Integer recompensaMonedas;

    @NotNull(message = "El nivel mínimo es obligatorio")
    private Integer nivelMinimo;

    @NotNull(message = "El tipo de misión es obligatorio")
    private Long idTipoMision;

    @NotNull(message = "El estado de misión es obligatorio")
    private Long idEstadoMision;
}