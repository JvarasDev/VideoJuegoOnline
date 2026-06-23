package cl.videojuego.pago_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComprobanteDTO {

    // Información del Pago
    private Long idPago;
    private Integer montoPagado;
    private LocalDateTime fechaPago;
    private String codigoTransaccion;
    private String metodoPago;
    private String estadoPago;

    // Información del Usuario
    private Long idUsuario;
    private String nombreCliente;
    private String correoCliente;
    private Integer nivelCuenta;

    // Información del Producto
    private Long idProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private String categoriaProducto;
}
