package cl.videojuego.usuario_service.repository;

import cl.videojuego.usuario_service.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository <Rol,Long> {
}
