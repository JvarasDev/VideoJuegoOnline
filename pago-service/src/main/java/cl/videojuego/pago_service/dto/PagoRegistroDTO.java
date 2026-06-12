package cl.videojuego.pago_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PagoRegistroDTO {

    @NotNull(message = "El usuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "El producto es obligatorio")
    private Long idProducto;

    @NotNull(message = "El monto es obligatorio")
    private Integer monto;

    @NotNull(message = "El método de pago es obligatorio")
    private Long idMetodoPago;

    @NotNull(message = "El estado del pago es obligatorio")
    private Long idEstadoPago;
}