package cl.videojuego.ranking_service.mapper;

import cl.videojuego.ranking_service.dto.PersonajeDTO;
import cl.videojuego.ranking_service.dto.RankingDTO;
import cl.videojuego.ranking_service.model.Ranking;

public class RankingMapper {

    public static RankingDTO toDTO(
            Ranking ranking,
            PersonajeDTO personaje
    ) {
        RankingDTO dto = new RankingDTO();

        dto.setIdRanking(ranking.getIdRanking());
        dto.setIdPersonaje(ranking.getIdPersonaje());
        dto.setNombrePersonaje(personaje.getNombre());
        dto.setPuntos(ranking.getPuntos());
        dto.setVictorias(ranking.getVictorias());
        dto.setDerrotas(ranking.getDerrotas());
        dto.setPosicion(ranking.getPosicion());
        dto.setNombreTemporada(ranking.getTemporada().getNombreTemporada());
        dto.setNombreLiga(ranking.getLiga().getNombreLiga());

        return dto;
    }
}