package cl.videojuego.usuario_service.exception;

/**
 * Excepción lanzada cuando no se encuentra un recurso en la base de datos.
 * Extiende RuntimeException para ser no verificada (unchecked).
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
