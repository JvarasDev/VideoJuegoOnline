package cl.videojuego.pago_service.repository;

import cl.videojuego.pago_service.model.EstadoPago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoPagoRepository
        extends JpaRepository<EstadoPago, Long> {

}