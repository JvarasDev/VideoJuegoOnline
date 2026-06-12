package cl.videojuego.mision_service.repository;

import cl.videojuego.mision_service.model.EstadoMision;

import org.springframework.data.jpa.repository.JpaRepository;

// Repository para acceder a estados_mision
public interface EstadoMisionRepository
        extends JpaRepository<EstadoMision, Long> {

}