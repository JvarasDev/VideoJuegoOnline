package cl.videojuego.pago_service.exception;

import org.springframework.http.HttpStatus;

public class PagoNotFoundException extends ApiException {
    public PagoNotFoundException(Long id) {
        super("Pago no encontrado con ID: " + id, HttpStatus.NOT_FOUND, "PAGO_NOT_FOUND");
    }
}
