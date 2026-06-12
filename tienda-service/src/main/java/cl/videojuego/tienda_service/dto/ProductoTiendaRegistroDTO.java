package cl.videojuego.tienda_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// DTO usado para crear productos en la tienda
@Data
public class ProductoTiendaRegistroDTO {

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombreProducto;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    private Integer precio;

    @NotNull(message = "El stock es obligatorio")
    private Integer stock;

    @NotNull(message = "La referencia del item (ID) es obligatoria")
    private Long idReferenciaItem;

    @NotNull(message = "El tipo de item es obligatorio")
    private Long idTipoItem;

    @NotNull(message = "La categoría es obligatoria")
    private Long idCategoriaProducto;

    @NotNull(message = "El estado es obligatorio")
    private Long idEstadoProducto;
}