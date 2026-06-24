package cl.videojuego.arma_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

// DTO que se devuelve como respuesta en Postman
@Schema(description = "Datos de un arma en el juego")
@Data
public class ArmaDTO {

    @Schema(description = "Identificador único del arma", example = "1")
    private Long idArma;

    @Schema(description = "Nombre descriptivo del arma", example = "Espada de fuego")
    private String nombreArma;

    @Schema(description = "Cantidad de daño base que inflige el arma", example = "150")
    private Integer danio;

    @Schema(description = "Nivel mínimo requerido para equipar el arma", example = "10")
    private Integer nivelMinimo;

    @Schema(description = "Precio en monedas del arma", example = "500")
    private Integer precio;

    @Schema(description = "Nombre del tipo de arma asociado", example = "Espada")
    private String nombreTipo;

    @Schema(description = "Rareza del arma", example = "Rara")
    private String nombreRareza;

    @Schema(description = "Multiplicador de daño calculado según la rareza", example = "1.5")
    private Double multiplicadorDanio;
}