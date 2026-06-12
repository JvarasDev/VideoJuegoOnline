package cl.videojuego.usuario_service.repository;

import cl.videojuego.usuario_service.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByCorreo(String correo);

    List<Usuario> findByRol_IdRol(Long idRol);

    List<Usuario> findByEstadoUsuario_IdEstadoUsuario(Long idEstadoUsuario);

    List<Usuario> findByNivelCuenta(Integer nivelCuenta);

    List<Usuario> findByFechaRegistroAfter(LocalDate fecha);

    List<Usuario> findByFechaRegistro(LocalDate fecha);
}
