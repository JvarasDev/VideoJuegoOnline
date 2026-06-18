package cl.videojuego.inventario_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "Datos requeridos para crear un inventario de personaje")
@Data
public class InventarioRegistroDTO {

    @Schema(description = "ID del personaje propietario del inventario", example = "1")
    @NotNull(message = "El personaje es obligatorio")
    private Long idPersonaje;

    @Schema(description = "Capacidad máxima de items del inventario", example = "20")
    @NotNull(message = "La capacidad máxima es obligatoria")
    private Integer capacidadMaxima;

    @Schema(description = "ID del estado del inventario", example = "1")
    @NotNull(message = "El estado del inventario es obligatorio")
    private Long idEstadoInventario;
}