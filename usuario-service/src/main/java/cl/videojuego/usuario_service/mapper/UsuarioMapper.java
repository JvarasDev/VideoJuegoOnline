package cl.videojuego.usuario_service.mapper;

import cl.videojuego.usuario_service.dto.UsuarioDTO;
import cl.videojuego.usuario_service.model.Usuario;

/**
 * Clase utilitaria para convertir entidades Usuario en DTOs.
 * Al ser solo métodos estáticos, se bloquea la instanciación.
 */
public final class UsuarioMapper {

    private UsuarioMapper() {
        // Clase utilitaria — no instanciar
    }

    /**
     * Convierte una entidad {@link Usuario} en su representación {@link UsuarioDTO}.
     *
     * @param usuario entidad a convertir
     * @return DTO con los datos públicos del usuario
     */
    public static UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getCorreo(),
                usuario.getNivelCuenta(),
                usuario.getRol().getNombreRol(),
                usuario.getEstadoUsuario().getNombreEstadoUsuario()
        );
    }
}
