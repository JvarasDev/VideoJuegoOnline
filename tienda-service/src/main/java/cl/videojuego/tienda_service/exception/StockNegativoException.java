package cl.videojuego.tienda_service.exception;

// Excepción lanzada al intentar asignar stock negativo (422 Unprocessable Entity)
public class StockNegativoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String ERROR_CODE = "STOCK_NEGATIVO";

    public StockNegativoException(String message) {
        super(message);
    }

    public StockNegativoException(Long idProducto, int valorInvalido) {
        super(String.format(
                "El stock del producto id=%d no puede ser negativo. Valor recibido: %d",
                idProducto, valorInvalido));
    }

    public StockNegativoException(String message, Throwable cause) {
        super(message, cause);
    }
}
