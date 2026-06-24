package cl.videojuego.ranking_service.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Estructura estándar de respuesta para errores de la API")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    
    @Schema(description = "Código de estado HTTP del error", example = "400")
    private int status;
    
    @Schema(description = "Razón general del error HTTP", example = "Bad Request")
    private String error;
    
    @Schema(description = "Mensaje detallado explicando el motivo del error", example = "Error de validación")
    private String message;
    
    @Schema(description = "Fecha y hora exacta en la que ocurrió el error", example = "2026-06-24T02:26:31.0983806")
    private LocalDateTime timestamp;
    
    @Schema(description = "Detalles adicionales, útil para errores de validación de campos", example = "{\"puntos\": \"Los puntos son obligatorios\"}")
    private Map<String, String> detalles;

    public ErrorResponse() {}

    public ErrorResponse(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(int status, String error, String message, Map<String, String> detalles) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.detalles = detalles;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public Map<String, String> getDetalles() { return detalles; }
    public void setDetalles(Map<String, String> detalles) { this.detalles = detalles; }
}