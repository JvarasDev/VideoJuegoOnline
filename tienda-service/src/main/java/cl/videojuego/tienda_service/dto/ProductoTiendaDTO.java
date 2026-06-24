package cl.videojuego.tienda_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Datos detallados de un producto en la tienda")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoTiendaDTO {

    @Schema(description = "Identificador único del producto", example = "1")
    private Long idProducto;

    @Schema(description = "Nombre del producto", example = "Poción de vida mayor")
    private String nombreProducto;

    @Schema(description = "Descripción detallada del producto", example = "Restaura 500 puntos de vida instantáneamente")
    private String descripcion;

    @Schema(description = "Precio del producto en monedas", example = "250")
    private Integer precio;

    @Schema(description = "Cantidad de unidades disponibles", example = "100")
    private Integer stock;

    @Schema(description = "ID del item de referencia asociado al producto", example = "3")
    private Long idReferenciaItem;

    @Schema(description = "Nombre del item de referencia", example = "Espada de Fuego")
    private String nombreItem;

    @Schema(description = "Tipo de item asociado (ej. ARMA, MISION)", example = "ARMA")
    private String nombreTipoItem;

    @Schema(description = "Nombre de la categoría del producto", example = "Consumibles")
    private String nombreCategoria;

    @Schema(description = "Estado actual del producto en la tienda", example = "DISPONIBLE")
    private String nombreEstado;
}