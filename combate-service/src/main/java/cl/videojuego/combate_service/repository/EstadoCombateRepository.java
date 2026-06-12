package cl.videojuego.combate_service.repository;

import cl.videojuego.combate_service.model.EstadoCombate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoCombateRepository extends JpaRepository<EstadoCombate, Long> {
}