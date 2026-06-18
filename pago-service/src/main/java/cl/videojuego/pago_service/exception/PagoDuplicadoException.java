package cl.videojuego.pago_service.exception;

public class PagoDuplicadoException extends RuntimeException {
    public PagoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
