package cl.videojuego.arma_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// DTO usado para crear o actualizar armas
@Schema(description = "Datos requeridos para crear o actualizar un arma")
@Data
public class ArmaRegistroDTO {

    @Schema(description = "Nombre del arma", example = "Espada de fuego")
    @NotBlank(message = "El nombre del arma es obligatorio")
    private String nombreArma;

    @Schema(description = "Daño que inflige el arma", example = "150")
    @NotNull(message = "El daño del arma es obligatorio")
    private Integer danio;

    @Schema(description = "Nivel mínimo requerido para usar el arma", example = "10")
    @NotNull(message = "El nivel mínimo es obligatorio")
    private Integer nivelMinimo;

    @Schema(description = "Precio del arma en monedas", example = "500")
    @NotNull(message = "El precio es obligatorio")
    private Integer precio;

    @Schema(description = "ID del tipo de arma", example = "1")
    @NotNull(message = "El tipo de arma es obligatorio")
    private Long idTipoArma;

    @Schema(description = "ID de la rareza del arma", example = "2")
    @NotNull(message = "La rareza del arma es obligatoria")
    private Long idRarezaArma;
}
