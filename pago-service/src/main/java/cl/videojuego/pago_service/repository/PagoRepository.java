package cl.videojuego.pago_service.repository;

import cl.videojuego.pago_service.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.math.BigDecimal;
import java.util.List;
public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByIdUsuario(Long idUsuario);

    List<Pago> findByIdProducto(Long idProducto);

    List<Pago> findByEstadoPago_IdEstadoPago(Long idEstadoPago);

    List<Pago> findByMetodoPago_IdMetodoPago(Long idMetodoPago);

    List<Pago> findByMontoLessThanEqual(BigDecimal monto);

    List<Pago> findByCodigoTransaccionContainingIgnoreCase(String codigoTransaccion);

}