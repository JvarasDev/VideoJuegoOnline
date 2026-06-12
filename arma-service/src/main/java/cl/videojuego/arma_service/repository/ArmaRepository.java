package cl.videojuego.arma_service.repository;

import cl.videojuego.arma_service.model.Arma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArmaRepository extends JpaRepository<Arma, Long> {

    // Buscar armas por tipo
    List<Arma> findByTipoArma_IdTipoArma(Long idTipoArma);

    // Buscar armas por rareza
    List<Arma> findByRarezaArma_IdRarezaArma(Long idRarezaArma);

    // Buscar armas por nivel mínimo
    List<Arma> findByNivelMinimo(Integer nivelMinimo);

    // Buscar armas que contengan nombre parecido
    List<Arma> findByNombreArmaContainingIgnoreCase(String nombreArma);

    // Buscar armas con precio menor o igual
    List<Arma> findByPrecioLessThanEqual(Integer precio);
}
