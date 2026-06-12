package cl.videojuego.inventario_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventarioRegistroDTO {

    @NotNull(message = "El personaje es obligatorio")
    private Long idPersonaje;

    @NotNull(message = "La capacidad máxima es obligatoria")
    private Integer capacidadMaxima;

    @NotNull(message = "El estado del inventario es obligatorio")
    private Long idEstadoInventario;
}