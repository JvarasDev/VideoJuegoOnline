package cl.videojuego.tienda_service.exception;

// Excepción lanzada cuando un producto consultado no existe (404 Not Found)
public class ProductoNoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String ERROR_CODE = "PRODUCTO_NO_ENCONTRADO";

    public ProductoNoEncontradoException(String message) {
        super(message);
    }

    public ProductoNoEncontradoException(Long idProducto) {
        super("No se encontró el producto con id: " + idProducto);
    }

    public ProductoNoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
