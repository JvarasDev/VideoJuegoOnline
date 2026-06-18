package cl.videojuego.ranking_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "Datos requeridos para registrar o actualizar un ranking de personaje")
@Data
public class RankingRegistroDTO {

    @Schema(description = "ID del personaje en el ranking", example = "1")
    @NotNull(message = "El personaje es obligatorio")
    private Long idPersonaje;

    @Schema(description = "Puntos acumulados en la temporada", example = "1500")
    @NotNull(message = "Los puntos son obligatorios")
    private Integer puntos;

    @Schema(description = "Número de victorias en la temporada", example = "30")
    @NotNull(message = "Las victorias son obligatorias")
    private Integer victorias;

    @Schema(description = "Número de derrotas en la temporada", example = "10")
    @NotNull(message = "Las derrotas son obligatorias")
    private Integer derrotas;

    @Schema(description = "Posición actual en el ranking", example = "5")
    @NotNull(message = "La posición es obligatoria")
    private Integer posicion;

    @Schema(description = "ID de la temporada", example = "3")
    @NotNull(message = "La temporada es obligatoria")
    private Long idTemporada;

    @Schema(description = "ID de la liga del ranking", example = "1")
    @NotNull(message = "La liga es obligatoria")
    private Long idLiga;
}