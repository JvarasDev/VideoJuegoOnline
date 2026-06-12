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
public class PagoDTO {

    private Long idPago;
    private Long idUsuario;
    private String correoUsuario;

    private Long idProducto;
    private String nombreProducto;

    private Integer monto;
    private LocalDateTime fechaPago;
    private String codigoTransaccion;

    private String nombreMetodo;
    private String nombreEstado;
}