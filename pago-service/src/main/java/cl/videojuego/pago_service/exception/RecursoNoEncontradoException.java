package cl.videojuego.pago_service.exception;

import org.springframework.http.HttpStatus;

public class RecursoNoEncontradoException extends ApiException{
    public RecursoNoEncontradoException (Long id){
        super( " Recurso no encontrado : " + id, HttpStatus.NOT_FOUND, "Recurso no encontrado por capa 8");
    }
}
