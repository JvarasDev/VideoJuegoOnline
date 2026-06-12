package cl.videojuego.arma_service.repository;

import cl.videojuego.arma_service.model.TipoArma;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository ya trae métodos como:
// findAll()
// findById()
// save()
// deleteById()

public interface TipoArmaRepository extends JpaRepository<TipoArma, Long> {

}
