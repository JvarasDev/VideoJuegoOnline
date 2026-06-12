package cl.videojuego.tienda_service.exception;

// Excepción genérica lanzada cuando un recurso (como categoría o estado) no existe (404 Not Found)
public class RecursoNoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String ERROR_CODE = "RECURSO_NO_ENCONTRADO";

    public RecursoNoEncontradoException(String message) {
        super(message);
    }
}