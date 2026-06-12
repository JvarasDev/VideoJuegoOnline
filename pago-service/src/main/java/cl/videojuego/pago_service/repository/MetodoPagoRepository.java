package cl.videojuego.pago_service.repository;

import cl.videojuego.pago_service.model.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetodoPagoRepository
        extends JpaRepository<MetodoPago, Long> {

}