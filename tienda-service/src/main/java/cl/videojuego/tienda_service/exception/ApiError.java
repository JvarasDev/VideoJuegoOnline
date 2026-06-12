package cl.videojuego.tienda_service.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

// Estructura de respuesta estándar para errores de la API
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private final int status;
    private final String error;
    private final String errorCode;
    private final String mensaje;
    private final String path;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime timestamp;

    private final List<CampoError> errores;

    public static ApiError of(HttpStatus status, String errorCode, String mensaje, String path) {
        return ApiError.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .errorCode(errorCode)
                .mensaje(mensaje)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ApiError ofValidacion(HttpStatus status, String errorCode,
            String mensaje, String path,
            List<CampoError> errores) {
        return ApiError.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .errorCode(errorCode)
                .mensaje(mensaje)
                .path(path)
                .timestamp(LocalDateTime.now())
                .errores(errores)
                .build();
    }

    public record CampoError(String campo, String mensaje) {
    }
}
