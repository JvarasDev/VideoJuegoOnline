package cl.videojuego.ranking_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Datos detallados de un registro de ranking")
@Data
public class RankingDTO {

    @Schema(description = "Identificador único del ranking", example = "1")
    private Long idRanking;

    @Schema(description = "Identificador único del personaje", example = "10")
    private Long idPersonaje;

    @Schema(description = "Nombre del personaje", example = "GuerreroMistico")
    private String nombrePersonaje;

    @Schema(description = "Cantidad de puntos acumulados", example = "1500")
    private Integer puntos;

    @Schema(description = "Cantidad total de victorias", example = "30")
    private Integer victorias;

    @Schema(description = "Cantidad total de derrotas", example = "10")
    private Integer derrotas;

    @Schema(description = "Posición en la liga actual", example = "5")
    private Integer posicion;

    @Schema(description = "Nombre de la temporada actual", example = "Temporada 1")
    private String nombreTemporada;

    @Schema(description = "Nombre de la liga en la que se encuentra", example = "Oro")
    private String nombreLiga;
}