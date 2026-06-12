package cl.videojuego.ranking_service.repository;

import cl.videojuego.ranking_service.model.Liga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigaRepository
        extends JpaRepository<Liga, Long> {

}