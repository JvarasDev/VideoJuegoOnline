package cl.videojuego.inventario_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "Datos requeridos para añadir un item a un inventario")
@Data
public class ItemInventarioRegistroDTO {

    @Schema(description = "ID del inventario al que pertenece el item", example = "1")
    @NotNull(message = "El inventario es obligatorio")
    private Long idInventario;

    @Schema(description = "ID de referencia del item (ID del arma, misión, etc.)", example = "5")
    @NotNull(message = "La referencia del item (ID) es obligatoria")
    private Long idReferenciaItem;

    @Schema(description = "ID del tipo de item (1=Arma, 2=Misión, etc.)", example = "1")
    @NotNull(message = "El tipo de item es obligatorio")
    private Long idTipoItem;

    @Schema(description = "Cantidad del item en el inventario", example = "3")
    @NotNull(message = "La cantidad es obligatoria")
    private Integer cantidad;

    @Schema(description = "Indica si el item está actualmente equipado", example = "false")
    @NotNull(message = "Debe indicar si está equipado")
    private Boolean equipado;
}