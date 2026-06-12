package cl.videojuego.mision_service.repository;

import cl.videojuego.mision_service.model.Mision;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repository principal de misiones
public interface MisionRepository
        extends JpaRepository<Mision, Long> {

    // Buscar misiones por tipo
    List<Mision>
    findByTipoMision_IdTipoMision(Long idTipoMision);

    // Buscar misiones por estado
    List<Mision>
    findByEstadoMision_IdEstadoMision(Long idEstadoMision);

    // Buscar misiones por nivel mínimo exacto
    List<Mision>
    findByNivelMinimo(Integer nivelMinimo);

    // Buscar misiones por nombre parecido
    List<Mision>
    findByNombreMisionContainingIgnoreCase(String nombreMision);

    // Buscar misiones por recompensa menor o igual
    List<Mision>
    findByRecompensaMonedasLessThanEqual(Integer recompensaMonedas);
}