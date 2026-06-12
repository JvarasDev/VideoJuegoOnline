package cl.videojuego.combate_service.mapper;

import cl.videojuego.combate_service.dto.CombateDTO;
import cl.videojuego.combate_service.dto.PersonajeDTO;
import cl.videojuego.combate_service.model.Combate;

public class CombateMapper {

    public static CombateDTO toDTO(
            Combate combate,
            PersonajeDTO atacante,
            PersonajeDTO defensor,
            PersonajeDTO ganador
    ) {
        CombateDTO dto = new CombateDTO();

        dto.setIdCombate(combate.getIdCombate());
        dto.setIdPersonajeAtacante(combate.getIdPersonajeAtacante());
        dto.setNombreAtacante(atacante.getNombre());

        dto.setIdPersonajeDefensor(combate.getIdPersonajeDefensor());
        dto.setNombreDefensor(defensor.getNombre());

        dto.setIdGanador(combate.getIdGanador());
        dto.setNombreGanador(ganador.getNombre());

        dto.setFechaCombate(combate.getFechaCombate());
        dto.setExperienciaGanada(combate.getExperienciaGanada());
        dto.setMonedasGanadas(combate.getMonedasGanadas());
        dto.setDuracionSegundos(combate.getDuracionSegundos());

        dto.setNombreTipo(combate.getTipoCombate().getNombreTipo());
        dto.setNombreEstado(combate.getEstadoCombate().getNombreEstado());

        return dto;
    }
}