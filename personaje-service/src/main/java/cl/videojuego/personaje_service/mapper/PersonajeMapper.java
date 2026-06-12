package cl.videojuego.personaje_service.mapper;

import cl.videojuego.personaje_service.dto.PersonajeDTO;
import cl.videojuego.personaje_service.model.Personaje;

public class PersonajeMapper {

    public static PersonajeDTO toDTO(Personaje personaje) {

        PersonajeDTO dto = new PersonajeDTO();

        dto.setIdPersonaje(personaje.getIdPersonaje());
        dto.setNombre(personaje.getNombre());
        dto.setNivel(personaje.getNivel());
        dto.setVida(personaje.getVida());
        dto.setMana(personaje.getMana());
        dto.setIdUsuario(personaje.getIdUsuario());

        dto.setNombreClase(
                personaje.getClasePersonaje().getNombreClase()
        );

        dto.setNombreEstado(
                personaje.getEstadoPersonaje().getNombreEstado()
        );

        return dto;
    }

}
