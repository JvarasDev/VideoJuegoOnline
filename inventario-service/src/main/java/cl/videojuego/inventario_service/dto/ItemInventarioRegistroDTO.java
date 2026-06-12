package cl.videojuego.inventario_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemInventarioRegistroDTO {

    @NotNull(message = "El inventario es obligatorio")
    private Long idInventario;

    @NotNull(message = "La referencia del item (ID) es obligatoria")
    private Long idReferenciaItem;

    @NotNull(message = "El tipo de item es obligatorio")
    private Long idTipoItem;

    @NotNull(message = "La cantidad es obligatoria")
    private Integer cantidad;

    @NotNull(message = "Debe indicar si está equipado")
    private Boolean equipado;
}