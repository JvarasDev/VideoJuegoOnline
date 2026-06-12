package cl.videojuego.ranking_service.dto;

import lombok.Data;

@Data
public class RankingDTO {

    private Long idRanking;
    private Long idPersonaje;
    private String nombrePersonaje;
    private Integer puntos;
    private Integer victorias;
    private Integer derrotas;
    private Integer posicion;
    private String nombreTemporada;
    private String nombreLiga;
}