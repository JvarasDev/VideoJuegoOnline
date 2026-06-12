package cl.videojuego.tienda_service.repository;

import cl.videojuego.tienda_service.model.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaProductoRepository
        extends JpaRepository<CategoriaProducto, Long> {

}