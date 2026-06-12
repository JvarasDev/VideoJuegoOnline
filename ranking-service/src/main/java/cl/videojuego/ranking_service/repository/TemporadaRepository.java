package cl.videojuego.ranking_service.repository;

import cl.videojuego.ranking_service.model.Temporada;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemporadaRepository
        extends JpaRepository<Temporada, Long> {

}