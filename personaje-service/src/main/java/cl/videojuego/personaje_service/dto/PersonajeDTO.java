package cl.videojuego.personaje_service.dto;

import lombok.Data;

@Data
public class PersonajeDTO {

    private Long idPersonaje;
    private String nombre;
    private Integer nivel;
    private Integer vida;
    private Integer mana;
    private Long idUsuario;
    private String nombreClase;
    private String nombreEstado;
}
