package cl.videojuego.pago_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "Datos requeridos para registrar un pago")
@Data
public class PagoRegistroDTO {

    @Schema(description = "ID del usuario que realiza el pago", example = "1")
    @NotNull(message = "El usuario es obligatorio")
    private Long idUsuario;

    @Schema(description = "ID del producto que se está pagando", example = "5")
    @NotNull(message = "El producto es obligatorio")
    private Long idProducto;

    @Schema(description = "Monto total del pago en monedas", example = "250")
    @NotNull(message = "El monto es obligatorio")
    private Integer monto;

    @Schema(description = "ID del método de pago utilizado", example = "1")
    @NotNull(message = "El método de pago es obligatorio")
    private Long idMetodoPago;

    @Schema(description = "ID del estado inicial del pago", example = "1")
    @NotNull(message = "El estado del pago es obligatorio")
    private Long idEstadoPago;
}