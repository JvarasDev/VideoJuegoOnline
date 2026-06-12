package cl.videojuego.tienda_service.dto;

import lombok.Data;

// DTO que representa la respuesta que viene desde arma-service
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
