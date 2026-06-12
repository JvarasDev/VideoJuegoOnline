package cl.videojuego.mision_service.mapper;

import cl.videojuego.mision_service.dto.MisionDTO;
import cl.videojuego.mision_service.model.Mision;

// Clase que transforma Entity Mision a MisionDTO
public class MisionMapper {

    public static MisionDTO toDTO(Mision mision) {

        MisionDTO dto = new MisionDTO();

        dto.setIdMision(mision.getIdMision());
        dto.setNombreMision(mision.getNombreMision());
        dto.setDescripcion(mision.getDescripcion());
        dto.setRecompensaExperiencia(mision.getRecompensaExperiencia());
        dto.setRecompensaMonedas(mision.getRecompensaMonedas());
        dto.setNivelMinimo(mision.getNivelMinimo());

        dto.setNombreTipo(
                mision.getTipoMision().getNombreTipo()
        );

        dto.setNombreEstado(
                mision.getEstadoMision().getNombreEstado()
        );

        return dto;
    }
}