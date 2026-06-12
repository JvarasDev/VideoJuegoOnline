package cl.videojuego.combate_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CombateRegistroDTO {

    @NotNull(message = "El personaje atacante es obligatorio")
    private Long idPersonajeAtacante;

    @NotNull(message = "El personaje defensor es obligatorio")
    private Long idPersonajeDefensor;

    @NotNull(message = "El ganador es obligatorio")
    private Long idGanador;

    @NotNull(message = "La experiencia ganada es obligatoria")
    private Integer experienciaGanada;

    @NotNull(message = "Las monedas ganadas son obligatorias")
    private Integer monedasGanadas;

    @NotNull(message = "La duración es obligatoria")
    private Integer duracionSegundos;

    @NotNull(message = "El tipo de combate es obligatorio")
    private Long idTipoCombate;

    @NotNull(message = "El estado de combate es obligatorio")
    private Long idEstadoCombate;
}