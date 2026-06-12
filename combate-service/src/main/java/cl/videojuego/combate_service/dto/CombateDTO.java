package cl.videojuego.combate_service.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CombateDTO {
    private Long idCombate;
    private Long idPersonajeAtacante;
    private String nombreAtacante;
    private Long idPersonajeDefensor;
    private String nombreDefensor;
    private Long idGanador;
    private String nombreGanador;
    private LocalDateTime fechaCombate;
    private Integer experienciaGanada;
    private Integer monedasGanadas;
    private Integer duracionSegundos;
    private String nombreTipo;
    private String nombreEstado;
}