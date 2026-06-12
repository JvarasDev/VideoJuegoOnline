package cl.videojuego.arma_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// DTO usado para crear o actualizar armas
@Data
public class ArmaRegistroDTO {

    @NotBlank(message = "El nombre del arma es obligatorio")
    private String nombreArma;

    @NotNull(message = "El daño del arma es obligatorio")
    private Integer danio;

    @NotNull(message = "El nivel mínimo es obligatorio")
    private Integer nivelMinimo;

    @NotNull(message = "El precio es obligatorio")
    private Integer precio;

    @NotNull(message = "El tipo de arma es obligatorio")
    private Long idTipoArma;

    @NotNull(message = "La rareza del arma es obligatoria")
    private Long idRarezaArma;
}
