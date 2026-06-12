package cl.videojuego.ranking_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RankingRegistroDTO {

    @NotNull(message = "El personaje es obligatorio")
    private Long idPersonaje;

    @NotNull(message = "Los puntos son obligatorios")
    private Integer puntos;

    @NotNull(message = "Las victorias son obligatorias")
    private Integer victorias;

    @NotNull(message = "Las derrotas son obligatorias")
    private Integer derrotas;

    @NotNull(message = "La posición es obligatoria")
    private Integer posicion;

    @NotNull(message = "La temporada es obligatoria")
    private Long idTemporada;

    @NotNull(message = "La liga es obligatoria")
    private Long idLiga;
}