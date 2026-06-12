package cl.videojuego.pago_service.dto;

import lombok.Data;

@Data
public class ProductoTiendaDTO {

    private Long idProducto;
    private String nombreProducto;
    private String descripcion;
    private Integer precio;
    private Integer stock;
    private Long idArma;
    private String nombreArma;
    private String nombreCategoria;
    private String nombreEstado;
}