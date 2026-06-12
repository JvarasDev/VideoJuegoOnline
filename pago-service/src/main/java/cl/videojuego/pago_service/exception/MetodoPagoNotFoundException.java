package cl.videojuego.pago_service.exception;

import org.springframework.http.HttpStatus;

public class MetodoPagoNotFoundException extends ApiException {
    public MetodoPagoNotFoundException(Long id) {
        super("Método de pago no encontrado con ID: " + id, HttpStatus.NOT_FOUND, "METODO_PAGO_NOT_FOUND");
    }
}
