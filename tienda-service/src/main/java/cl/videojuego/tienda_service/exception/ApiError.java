package cl.videojuego.tienda_service.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Estructura de respuesta estándar para errores de la API")
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    @Schema(description = "Código de estado HTTP", example = "400")
    private final int status;
    
    @Schema(description = "Razón general del error HTTP", example = "Bad Request")
    private final String error;
    
    @Schema(description = "Código de error interno específico de la aplicación", example = "ERR_BAD_REQUEST_400")
    private final String errorCode;
    
    @Schema(description = "Mensaje detallado explicando el motivo del error", example = "Error de validación")
    private final String mensaje;
    
    @Schema(description = "Ruta o URI donde ocurrió el error", example = "/api/productos")
    private final String path;

    @Schema(description = "Fecha y hora exacta en la que ocurrió el error", example = "2026-06-24T02:26:31")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime timestamp;

    @Schema(description = "Lista de errores detallados por campo (útil en validaciones)")
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

    @Schema(description = "Representación del error específico de un campo")
    public record CampoError(
            @Schema(description = "Nombre del campo que tiene el error", example = "precio")
            String campo,
            
            @Schema(description = "Mensaje de error para el campo", example = "El precio es obligatorio")
            String mensaje
    ) {}
}