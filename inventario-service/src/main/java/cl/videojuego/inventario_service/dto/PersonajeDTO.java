package cl.videojuego.inventario_service.dto;

import lombok.Data;

@Data
public class PersonajeDTO {

    private Long idPersonaje;
    private Long idUsuario;
    private String nombre;
    private Integer nivel;
    private Integer vida;
    private Integer mana;
    private String nombreClase;
    private String nombreEstado;
}