package cl.videojuego.usuario_service.repository;

import cl.videojuego.usuario_service.model.EstadoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoUsuarioRepository extends JpaRepository<EstadoUsuario,Long> {
}
