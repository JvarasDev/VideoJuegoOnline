package cl.videojuego.combate_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "Datos requeridos para registrar un combate")
@Data
public class CombateRegistroDTO {

    @Schema(description = "ID del personaje atacante", example = "1")
    @NotNull(message = "El personaje atacante es obligatorio")
    private Long idPersonajeAtacante;

    @Schema(description = "ID del personaje defensor", example = "2")
    @NotNull(message = "El personaje defensor es obligatorio")
    private Long idPersonajeDefensor;

    @Schema(description = "ID del personaje ganador del combate", example = "1")
    @NotNull(message = "El ganador es obligatorio")
    private Long idGanador;

    @Schema(description = "Experiencia ganada al terminar el combate", example = "200")
    @NotNull(message = "La experiencia ganada es obligatoria")
    private Integer experienciaGanada;

    @Schema(description = "Monedas ganadas al ganar el combate", example = "50")
    @NotNull(message = "Las monedas ganadas son obligatorias")
    private Integer monedasGanadas;

    @Schema(description = "Duración del combate en segundos", example = "120")
    @NotNull(message = "La duración es obligatoria")
    private Integer duracionSegundos;

    @Schema(description = "ID del tipo de combate", example = "1")
    @NotNull(message = "El tipo de combate es obligatorio")
    private Long idTipoCombate;

    @Schema(description = "ID del estado del combate", example = "1")
    @NotNull(message = "El estado de combate es obligatorio")
    private Long idEstadoCombate;
}