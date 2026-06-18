package cl.videojuego.usuario_service.service;

import cl.videojuego.usuario_service.dto.UsuarioDTO;
import cl.videojuego.usuario_service.dto.UsuarioRegistroDTO;
import cl.videojuego.usuario_service.exception.RecursoNoEncontradoException;
import cl.videojuego.usuario_service.mapper.UsuarioMapper;
import cl.videojuego.usuario_service.model.EstadoUsuario;
import cl.videojuego.usuario_service.model.Rol;
import cl.videojuego.usuario_service.model.Usuario;
import cl.videojuego.usuario_service.repository.EstadoUsuarioRepository;
import cl.videojuego.usuario_service.repository.RolRepository;
import cl.videojuego.usuario_service.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Servicio que contiene la lógica de negocio relacionada con los usuarios.
 */
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final EstadoUsuarioRepository estadoUsuarioRepository;

    // ─── Consultas ────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .map(UsuarioMapper::toDTO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + idUsuario));
    }

    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .map(UsuarioMapper::toDTO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con correo: " + correo));
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarPorRol(Long idRol) {
        return usuarioRepository.findByRol_IdRol(idRol)
                .stream()
                .map(UsuarioMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarPorEstado(Long idEstadoUsuario) {
        return usuarioRepository.findByEstadoUsuario_IdEstadoUsuario(idEstadoUsuario)
                .stream()
                .map(UsuarioMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarPorNivel(Integer nivelCuenta) {
        return usuarioRepository.findByNivelCuenta(nivelCuenta)
                .stream()
                .map(UsuarioMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarRegistradosDespuesDe(LocalDate fecha) {
        return usuarioRepository.findByFechaRegistroAfter(fecha)
                .stream()
                .map(UsuarioMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarPorFecha(LocalDate fecha) {
        return usuarioRepository.findByFechaRegistro(fecha)
                .stream()
                .map(UsuarioMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarPorFechaVerificandoResultados(LocalDate fecha) {
        List<UsuarioDTO> usuarios = listarPorFecha(fecha);
        if (usuarios.isEmpty()) {
            throw new RecursoNoEncontradoException("No hay usuarios registrados en la fecha: " + fecha);
        }
        return usuarios;
    }

    // ─── Escritura ────────────────────────────────────────────────────────────

    @Transactional
    public UsuarioDTO registrarUsuario(UsuarioRegistroDTO dto) {
        Rol rol = buscarRol(dto.idRol());
        EstadoUsuario estado = buscarEstado(dto.idEstadoUsuario());

        Usuario usuario = new Usuario();
        usuario.setFechaRegistro(LocalDate.now());
        usuario.setNivelCuenta(1);
        aplicarDatosDTO(usuario, dto, rol, estado);

        return UsuarioMapper.toDTO(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioDTO actualizarUsuario(Long idUsuario, UsuarioRegistroDTO dto) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + idUsuario));

        Rol rol = buscarRol(dto.idRol());
        EstadoUsuario estado = buscarEstado(dto.idEstadoUsuario());
        aplicarDatosDTO(usuario, dto, rol, estado);

        return UsuarioMapper.toDTO(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioDTO actualizarRolYEstado(Long idUsuario, Long idRol, Long idEstadoUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + idUsuario));

        usuario.setRol(buscarRol(idRol));
        usuario.setEstadoUsuario(buscarEstado(idEstadoUsuario));

        return UsuarioMapper.toDTO(usuarioRepository.save(usuario));
    }

    @Transactional
    public void eliminarUsuario(Long idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RecursoNoEncontradoException("Usuario no encontrado con ID: " + idUsuario);
        }
        usuarioRepository.deleteById(idUsuario);
    }

    // ─── Helpers privados ─────────────────────────────────────────────────────

    private Rol buscarRol(Long idRol) {
        return rolRepository.findById(idRol)
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol no encontrado con ID: " + idRol));
    }

    private EstadoUsuario buscarEstado(Long idEstado) {
        return estadoUsuarioRepository.findById(idEstado)
                .orElseThrow(() -> new RecursoNoEncontradoException("Estado de usuario no encontrado con ID: " + idEstado));
    }

    /**
     * Aplica los campos comunes de un DTO sobre una entidad Usuario.
     * Evita duplicar la asignación de campos entre registrar y actualizar.
     */
    private void aplicarDatosDTO(Usuario usuario, UsuarioRegistroDTO dto, Rol rol, EstadoUsuario estado) {
        usuario.setNombre(dto.nombre());
        usuario.setApellido(dto.apellido());
        usuario.setCorreo(dto.correo());
        usuario.setContrasena(dto.contrasena());
        usuario.setRol(rol);
        usuario.setEstadoUsuario(estado);
    }
}
