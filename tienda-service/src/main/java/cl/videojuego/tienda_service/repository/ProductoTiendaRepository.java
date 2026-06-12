package cl.videojuego.tienda_service.repository;

import cl.videojuego.tienda_service.model.ProductoTienda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoTiendaRepository
        extends JpaRepository<ProductoTienda, Long> {

    // Buscar productos por categoría
    List<ProductoTienda>
    findByCategoriaProducto_IdCategoriaProducto(Long idCategoriaProducto);

    // Buscar productos por estado
    List<ProductoTienda>
    findByEstadoProducto_IdEstadoProducto(Long idEstadoProducto);

    // Buscar productos por precio máximo
    List<ProductoTienda>
    findByPrecioLessThanEqual(Integer precio);

    // Buscar productos por nombre parecido
    List<ProductoTienda>
    findByNombreProductoContainingIgnoreCase(String nombreProducto);

    // Buscar productos con stock mayor a cierto valor
    List<ProductoTienda>
    findByStockGreaterThan(Integer stock);
}