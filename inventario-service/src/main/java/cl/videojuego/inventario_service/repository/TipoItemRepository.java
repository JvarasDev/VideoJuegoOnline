package cl.videojuego.inventario_service.repository;

import cl.videojuego.inventario_service.model.TipoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoItemRepository extends JpaRepository<TipoItem, Long> {
}
