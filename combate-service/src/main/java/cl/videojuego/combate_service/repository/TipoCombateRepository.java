package cl.videojuego.combate_service.repository;

import cl.videojuego.combate_service.model.TipoCombate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoCombateRepository extends JpaRepository<TipoCombate, Long> {
}