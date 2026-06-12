package cl.videojuego.arma_service.mapper;

import cl.videojuego.arma_service.dto.ArmaDTO;
import cl.videojuego.arma_service.model.Arma;

// Convierte Entity Arma hacia ArmaDTO
public class ArmaMapper {

    public static ArmaDTO toDTO(Arma arma) {

        ArmaDTO dto = new ArmaDTO();

        dto.setIdArma(arma.getIdArma());
        dto.setNombreArma(arma.getNombreArma());
        dto.setDanio(arma.getDanio());
        dto.setNivelMinimo(arma.getNivelMinimo());
        dto.setPrecio(arma.getPrecio());

        dto.setNombreTipo(
                arma.getTipoArma().getNombreTipo()
        );

        dto.setNombreRareza(
                arma.getRarezaArma().getNombreRareza()
        );

        dto.setMultiplicadorDanio(
                arma.getRarezaArma().getMultiplicadorDanio()
        );

        return dto;
    }
}
