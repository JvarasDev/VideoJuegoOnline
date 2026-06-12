package cl.videojuego.usuario_service.service;

import cl.videojuego.usuario_service.exception.RecursoNoEncontradoException;
import cl.videojuego.usuario_service.model.Rol;
import cl.videojuego.usuario_service.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio para gestionar los roles de usuario (admin, jugador, moderador, etc.).
 */
@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;

    @Transactional(readOnly = true)
    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Rol buscarPorId(Long idRol) {
        return rolRepository.findById(idRol)
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol no encontrado con ID: " + idRol));
    }

    @Transactional
    public Rol guardarRol(Rol rol) {
        return rolRepository.save(rol);
    }

    @Transactional
    public void eliminarRol(Long idRol) {
        if (!rolRepository.existsById(idRol)) {
            throw new RecursoNoEncontradoException("Rol no encontrado con ID: " + idRol);
        }
        rolRepository.deleteById(idRol);
    }
}
