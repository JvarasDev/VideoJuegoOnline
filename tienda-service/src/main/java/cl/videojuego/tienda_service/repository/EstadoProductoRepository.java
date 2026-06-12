
package cl.videojuego.tienda_service.repository;

import cl.videojuego.tienda_service.model.EstadoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoProductoRepository
        extends JpaRepository<EstadoProducto, Long> {

}