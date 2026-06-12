package cl.videojuego.tienda_service.exception;

// Excepción lanzada cuando el stock disponible es insuficiente (409 Conflict)
public class StockInsuficienteException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String ERROR_CODE = "STOCK_INSUFICIENTE";

    public StockInsuficienteException(String message) {
        super(message);
    }

    public StockInsuficienteException(Long idProducto, int solicitado, int disponible) {
        super(String.format(
                "Stock insuficiente para el producto id=%d: solicitado=%d, disponible=%d",
                idProducto, solicitado, disponible));
    }

    public StockInsuficienteException(String message, Throwable cause) {
        super(message, cause);
    }
}
