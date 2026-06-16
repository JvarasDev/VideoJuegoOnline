package cl.videojuego.pago_service.exception;

import org.springframework.http.HttpStatus;

public class MetodoPagoNotFoundException extends ApiException {
    public MetodoPagoNotFoundException(Long id) {
        super("Método de pago no encontrado con ID: " + id, HttpStatus.UNAUTHORIZED , "METODO_PAGO_NOT_FOUND");
        //errorCode="404":metodo dirigido hacia la logica de negocio puede ser global y a la vez especifico de algun metodo como verificacion
    }
}
