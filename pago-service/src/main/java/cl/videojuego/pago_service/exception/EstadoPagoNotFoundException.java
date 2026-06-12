package cl.videojuego.pago_service.exception;

import org.springframework.http.HttpStatus;

public class EstadoPagoNotFoundException extends ApiException {
    public EstadoPagoNotFoundException(Long id) {
        super("Estado de pago no encontrado con ID: " + id, HttpStatus.NOT_FOUND, "ESTADO_PAGO_NOT_FOUND");
    }
}
