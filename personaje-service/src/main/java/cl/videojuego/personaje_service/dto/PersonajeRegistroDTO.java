package cl.videojuego.personaje_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PersonajeRegistroDTO {

    @NotBlank(message = "El nombre del personaje es obligatorio")
    private String nombre;

    @NotNull(message = "El usuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "La clase del personaje es obligatoria")
    private Long idClasePersonaje;

    @NotNull(message = "El estado del personaje es obligatorio")
    private Long idEstadoPersonaje;
}
