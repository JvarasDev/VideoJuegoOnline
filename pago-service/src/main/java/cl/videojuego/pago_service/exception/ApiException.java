package cl.videojuego.pago_service.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiException extends RuntimeException {
    private final HttpStatus status;
    private final String errorCode;


    public ApiException(String message, HttpStatus status, String errorCode){
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }



}
