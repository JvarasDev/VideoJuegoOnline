package cl.videojuego.personaje_service.exception;

public class UsuarioNoAptoException extends RuntimeException {
    public UsuarioNoAptoException(String message) {
        super(message);
    }
}
