package cl.videojuego.arma_service.dto;

import lombok.Data;

// DTO que se devuelve como respuesta en Postman
@Data
public class ArmaDTO {

    private Long idArma;

    private String nombreArma;

    private Integer danio;

    private Integer nivelMinimo;

    private Integer precio;

    private String nombreTipo;

    private String nombreRareza;

    private Double multiplicadorDanio;
}