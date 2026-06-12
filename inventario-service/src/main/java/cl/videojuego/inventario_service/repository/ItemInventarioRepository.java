package cl.videojuego.inventario_service.repository;

import cl.videojuego.inventario_service.model.ItemInventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemInventarioRepository
        extends JpaRepository<ItemInventario, Long> {

    List<ItemInventario> findByInventario_IdInventario(Long idInventario);

    List<ItemInventario> findByIdReferenciaItem(Long idReferenciaItem);

    List<ItemInventario> findByEquipado(Boolean equipado);
}