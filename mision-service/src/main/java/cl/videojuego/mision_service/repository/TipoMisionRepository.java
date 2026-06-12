package cl.videojuego.mision_service.repository;

// Importamos la entidad TipoMision
import cl.videojuego.mision_service.model.TipoMision;

// JpaRepository ya trae métodos automáticos:
// save()
// findAll()
// findById()
// deleteById()
import org.springframework.data.jpa.repository.JpaRepository;

// Repository encargado de acceder a la tabla tipos_mision
public interface TipoMisionRepository
        extends JpaRepository<TipoMision, Long> {

}