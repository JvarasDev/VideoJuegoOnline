package cl.videojuego.pago_service.exception;

import org.springframework.http.HttpStatus;

public class PagoInsuficienteException extends ApiException {
    public   PagoInsuficienteException(Long id) {
        super("fondos insuficientes : " + id, HttpStatus.UNAUTHORIZED , "METODO_PAGO_NOT_FOUND");
}
}