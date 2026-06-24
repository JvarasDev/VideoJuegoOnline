package cl.videojuego.combate_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Schema(description = "Datos detallados de un combate")
@Data
public class CombateDTO {
    
    @Schema(description = "Identificador único del combate", example = "1")
    private Long idCombate;
    
    @Schema(description = "Identificador único del personaje atacante", example = "1")
    private Long idPersonajeAtacante;
    
    @Schema(description = "Nombre del personaje atacante", example = "Guerrero")
    private String nombreAtacante;
    
    @Schema(description = "Identificador único del personaje defensor", example = "2")
    private Long idPersonajeDefensor;
    
    @Schema(description = "Nombre del personaje defensor", example = "Mago")
    private String nombreDefensor;
    
    @Schema(description = "Identificador único del personaje ganador", example = "1")
    private Long idGanador;
    
    @Schema(description = "Nombre del personaje ganador", example = "Guerrero")
    private String nombreGanador;
    
    @Schema(description = "Fecha y hora en la que se realizó el combate", example = "2026-06-24T02:26:31.0983806")
    private LocalDateTime fechaCombate;
    
    @Schema(description = "Cantidad de experiencia ganada en el combate", example = "250")
    private Integer experienciaGanada;
    
    @Schema(description = "Cantidad de monedas ganadas en el combate", example = "100")
    private Integer monedasGanadas;
    
    @Schema(description = "Duración total del combate en segundos", example = "45")
    private Integer duracionSegundos;
    
    @Schema(description = "Nombre del tipo de combate", example = "PVP")
    private String nombreTipo;
    
    @Schema(description = "Nombre del estado del combate", example = "FINALIZADO")
    private String nombreEstado;
}