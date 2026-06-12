package cl.videojuego.inventario_service.repository;

import cl.videojuego.inventario_service.model.EstadoInventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoInventarioRepository
        extends JpaRepository<EstadoInventario, Long> {

}