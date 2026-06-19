package cl.videojuego.usuario_service.service;

import cl.videojuego.usuario_service.exception.RecursoNoEncontradoException;
import cl.videojuego.usuario_service.model.EstadoUsuario;
import cl.videojuego.usuario_service.repository.EstadoUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio para gestionar los estados de usuario (activo, inactivo, bloqueado, etc.).
 */
@Service
@SuppressWarnings("null")
@RequiredArgsConstructor
public class EstadoUsuarioService {

    private final EstadoUsuarioRepository estadoUsuarioRepository;

    @Transactional(readOnly = true)
    public List<EstadoUsuario> listarEstados() {
        return estadoUsuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public EstadoUsuario buscarPorId(Long idEstadoUsuario) {
        return estadoUsuarioRepository.findById(idEstadoUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Estado no encontrado con ID: " + idEstadoUsuario));
    }

    @Transactional
    public EstadoUsuario guardarEstado(EstadoUsuario estadoUsuario) {
        return estadoUsuarioRepository.save(estadoUsuario);
    }

    @Transactional
    public void eliminarEstado(Long idEstadoUsuario) {
        if (!estadoUsuarioRepository.existsById(idEstadoUsuario)) {
            throw new RecursoNoEncontradoException("Estado no encontrado con ID: " + idEstadoUsuario);
        }
        estadoUsuarioRepository.deleteById(idEstadoUsuario);
    }
}
