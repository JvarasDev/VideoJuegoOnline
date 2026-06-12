package cl.videojuego.personaje_service.repository;

import cl.videojuego.personaje_service.model.ClasePersonaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClasePersonajeRepository extends JpaRepository<ClasePersonaje, Long> {
}