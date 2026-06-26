package cl.videojuego.inventario_service.exception;

public class ConflictoNegocioException extends RuntimeException {
    public ConflictoNegocioException(String message) {
        super(message);
    }
}
