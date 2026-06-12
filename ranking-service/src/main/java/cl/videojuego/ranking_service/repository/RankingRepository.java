package cl.videojuego.ranking_service.repository;

import cl.videojuego.ranking_service.model.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RankingRepository
        extends JpaRepository<Ranking, Long> {

    List<Ranking> findByIdPersonaje(Long idPersonaje);

    List<Ranking> findByLiga_IdLiga(Long idLiga);

    List<Ranking> findByTemporada_IdTemporada(Long idTemporada);

    List<Ranking> findByPuntosGreaterThanEqual(Integer puntos);

    List<Ranking> findTop10ByOrderByPuntosDesc();
}