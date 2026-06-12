package cl.videojuego.tienda_service.exception;

import java.math.BigDecimal;

// Excepción lanzada al intentar registrar o actualizar un producto con precio negativo (422 Unprocessable Entity)
public class PrecioNegativoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String ERROR_CODE = "PRECIO_NEGATIVO";

    public PrecioNegativoException(String message) {
        super(message);
    }

    public PrecioNegativoException(Long idProducto, BigDecimal precioInvalido) {
        super(String.format(
                "El precio del producto id=%d no puede ser negativo. Valor recibido: %s",
                idProducto, precioInvalido));
    }

    public PrecioNegativoException(String message, Throwable cause) {
        super(message, cause);
    }
}
