package cl.videojuego.tienda_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "Datos requeridos para crear o actualizar un producto en la tienda")
@Data
public class ProductoTiendaRegistroDTO {

    @Schema(description = "Nombre del producto", example = "Poción de vida mayor")
    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombreProducto;

    @Schema(description = "Descripción del producto", example = "Restaura 500 puntos de vida instantáneamente")
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @Schema(description = "Precio del producto en monedas", example = "250")
    @NotNull(message = "El precio es obligatorio")
    private Integer precio;

    @Schema(description = "Unidades disponibles en stock", example = "100")
    @NotNull(message = "El stock es obligatorio")
    private Integer stock;

    @Schema(description = "ID de referencia del item asociado (arma, misión, etc.)", example = "3")
    @NotNull(message = "La referencia del item (ID) es obligatoria")
    private Long idReferenciaItem;

    @Schema(description = "ID del tipo de item", example = "1")
    @NotNull(message = "El tipo de item es obligatorio")
    private Long idTipoItem;

    @Schema(description = "ID de la categoría del producto", example = "2")
    @NotNull(message = "La categoría es obligatoria")
    private Long idCategoriaProducto;

    @Schema(description = "ID del estado del producto", example = "1")
    @NotNull(message = "El estado es obligatorio")
    private Long idEstadoProducto;
}