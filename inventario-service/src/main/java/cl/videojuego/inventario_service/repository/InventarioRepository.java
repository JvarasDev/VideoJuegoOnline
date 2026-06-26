package cl.videojuego.inventario_service.repository;

import cl.videojuego.inventario_service.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventarioRepository
        extends JpaRepository<Inventario, Long> {

    List<Inventario> findByIdPersonaje(Long idPersonaje);

    List<Inventario> findByEstadoInventario_IdEstadoInventario(Long idEstadoInventario);

    boolean existsByIdPersonaje(Long idPersonaje);
}