package cl.videojuego.tienda_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoTiendaDTO {

    private Long idProducto;

    private String nombreProducto;

    private String descripcion;

    private Integer precio;

    private Integer stock;

    private Long idReferenciaItem;

    private String nombreItem;

    private String nombreTipoItem;

    private String nombreCategoria;

    private String nombreEstado;
}
