package cl.videojuego.mision_service.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Estructura estándar de respuesta para errores de la API")
public class ErrorResponse {
    
    @Schema(description = "Código de estado HTTP del error", example = "404")
    private int status;
    
    @Schema(description = "Razón general del error HTTP", example = "Not Found")
    private String error;
    
    @Schema(description = "Mensaje detallado explicando el motivo del error", example = "Misión no encontrada")
    private String message;
    
    @Schema(description = "Fecha y hora exacta en la que ocurrió el error", example = "2026-06-24T02:26:31.0983806")
    private LocalDateTime timestamp;

    public ErrorResponse() {}

    public ErrorResponse(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
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
}