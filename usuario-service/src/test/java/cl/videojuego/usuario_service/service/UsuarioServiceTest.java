package cl.videojuego.usuario_service.service;

import cl.videojuego.usuario_service.dto.UsuarioDTO;
import cl.videojuego.usuario_service.dto.UsuarioRegistroDTO;
import cl.videojuego.usuario_service.exception.RecursoNoEncontradoException;
import cl.videojuego.usuario_service.model.EstadoUsuario;
import cl.videojuego.usuario_service.model.Rol;
import cl.videojuego.usuario_service.model.Usuario;
import cl.videojuego.usuario_service.repository.EstadoUsuarioRepository;
import cl.videojuego.usuario_service.repository.RolRepository;
import cl.videojuego.usuario_service.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private EstadoUsuarioRepository estadoUsuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioMock;
    private Rol rolMock;
    private EstadoUsuario estadoMock;
    private UsuarioRegistroDTO usuarioRegistroDTO;

    @BeforeEach
    void setUp() {
        rolMock = new Rol();
        rolMock.setIdRol(1L);
        rolMock.setNombreRol("JUGADOR");

        estadoMock = new EstadoUsuario();
        estadoMock.setIdEstadoUsuario(1L);
        estadoMock.setNombreEstadoUsuario("ACTIVO");

        usuarioMock = new Usuario();
        usuarioMock.setIdUsuario(1L);
        usuarioMock.setNombre("Juan");
        usuarioMock.setApellido("Perez");
        usuarioMock.setCorreo("juan@test.com");
        usuarioMock.setContrasena("secreta123");
        usuarioMock.setFechaRegistro(LocalDate.now());
        usuarioMock.setNivelCuenta(1);
        usuarioMock.setRol(rolMock);
        usuarioMock.setEstadoUsuario(estadoMock);

        usuarioRegistroDTO = new UsuarioRegistroDTO(
                "Juan", "Perez", "juan@test.com", "secreta123", 1L, 1L
        );
    }

    @Test
    void shouldReturnUserList_WhenListarUsuariosIsCalled() {
        // Given
        given(usuarioRepository.findAll()).willReturn(List.of(usuarioMock));

        // When
        List<UsuarioDTO> result = usuarioService.listarUsuarios();

        // Then
        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.get(0).correo()).isEqualTo("juan@test.com");
        verify(usuarioRepository, times(1)).findAll();
        verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    void shouldReturnEmptyList_WhenListarUsuariosFindsNoData() {
        // Given
        given(usuarioRepository.findAll()).willReturn(Collections.emptyList());

        // When
        List<UsuarioDTO> result = usuarioService.listarUsuarios();

        // Then
        assertThat(result).isNotNull().isEmpty();
        verify(usuarioRepository, times(1)).findAll();
        verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    void shouldReturnUser_WhenBuscarPorIdIsSuccessful() {
        // Given
        given(usuarioRepository.findById(1L)).willReturn(Optional.of(usuarioMock));

        // When
        UsuarioDTO result = usuarioService.buscarPorId(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.idUsuario()).isEqualTo(1L);
        assertThat(result.correo()).isEqualTo("juan@test.com");
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowRecursoNoEncontradoException_WhenBuscarPorIdFails() {
        // Given
        given(usuarioRepository.findById(99L)).willReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> usuarioService.buscarPorId(99L))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Usuario no encontrado con ID: 99");
        verify(usuarioRepository, times(1)).findById(99L);
    }

    @Test
    void shouldReturnUser_WhenBuscarPorCorreoIsSuccessful() {
        // Given
        given(usuarioRepository.findByCorreo("juan@test.com")).willReturn(Optional.of(usuarioMock));

        // When
        UsuarioDTO result = usuarioService.buscarPorCorreo("juan@test.com");

        // Then
        assertThat(result).isNotNull();
        assertThat(result.correo()).isEqualTo("juan@test.com");
        verify(usuarioRepository, times(1)).findByCorreo("juan@test.com");
    }

    @Test
    void shouldThrowRecursoNoEncontradoException_WhenBuscarPorCorreoFails() {
        // Given
        given(usuarioRepository.findByCorreo("noexiste@test.com")).willReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> usuarioService.buscarPorCorreo("noexiste@test.com"))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Usuario no encontrado con correo: noexiste@test.com");
        verify(usuarioRepository, times(1)).findByCorreo("noexiste@test.com");
    }

    @Test
    void shouldReturnUserDTO_WhenRegistrarUsuarioIsSuccessful() {
        // Given
        given(rolRepository.findById(1L)).willReturn(Optional.of(rolMock));
        given(estadoUsuarioRepository.findById(1L)).willReturn(Optional.of(estadoMock));
        given(usuarioRepository.save(any(Usuario.class))).willReturn(usuarioMock);

        // When
        UsuarioDTO result = usuarioService.registrarUsuario(usuarioRegistroDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.correo()).isEqualTo("juan@test.com");
        assertThat(result.nombreRol()).isEqualTo("JUGADOR");
        verify(rolRepository, times(1)).findById(1L);
        verify(estadoUsuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void shouldThrowException_WhenRegistrarUsuarioHasInvalidRol() {
        // Given
        given(rolRepository.findById(1L)).willReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> usuarioService.registrarUsuario(usuarioRegistroDTO))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Rol no encontrado con ID: 1");
        
        verify(rolRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(estadoUsuarioRepository, usuarioRepository);
    }

    @Test
    void shouldReturnUserDTO_WhenActualizarUsuarioIsSuccessful() {
        // Given
        UsuarioRegistroDTO updateDTO = new UsuarioRegistroDTO(
                "Juan Editado", "Perez Editado", "juan.editado@test.com", "nuevaPass", 1L, 1L
        );
        Usuario usuarioEditado = new Usuario();
        usuarioEditado.setIdUsuario(1L);
        usuarioEditado.setNombre("Juan Editado");
        usuarioEditado.setApellido("Perez Editado");
        usuarioEditado.setCorreo("juan.editado@test.com");
        usuarioEditado.setRol(rolMock);
        usuarioEditado.setEstadoUsuario(estadoMock);

        given(usuarioRepository.findById(1L)).willReturn(Optional.of(usuarioMock));
        given(rolRepository.findById(1L)).willReturn(Optional.of(rolMock));
        given(estadoUsuarioRepository.findById(1L)).willReturn(Optional.of(estadoMock));
        given(usuarioRepository.save(any(Usuario.class))).willReturn(usuarioEditado);

        // When
        UsuarioDTO result = usuarioService.actualizarUsuario(1L, updateDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.nombre()).isEqualTo("Juan Editado");
        verify(usuarioRepository, times(1)).findById(1L);
        verify(rolRepository, times(1)).findById(1L);
        verify(estadoUsuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void shouldThrowException_WhenActualizarUsuarioFindsNoUser() {
        // Given
        given(usuarioRepository.findById(99L)).willReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> usuarioService.actualizarUsuario(99L, usuarioRegistroDTO))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Usuario no encontrado con ID: 99");

        verify(usuarioRepository, times(1)).findById(99L);
        verifyNoMoreInteractions(rolRepository, estadoUsuarioRepository);
    }

    @Test
    void shouldDeleteUser_WhenEliminarUsuarioIsSuccessful() {
        // Given
        given(usuarioRepository.existsById(1L)).willReturn(true);

        // When
        usuarioService.eliminarUsuario(1L);

        // Then
        verify(usuarioRepository, times(1)).existsById(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowException_WhenEliminarUsuarioFindsNoUser() {
        // Given
        given(usuarioRepository.existsById(99L)).willReturn(false);

        // When / Then
        assertThatThrownBy(() -> usuarioService.eliminarUsuario(99L))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Usuario no encontrado con ID: 99");

        verify(usuarioRepository, times(1)).existsById(99L);
        verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    void shouldReturnUserDTO_WhenActualizarRolYEstadoIsSuccessful() {
        // Given
        Rol nuevoRol = new Rol();
        nuevoRol.setIdRol(2L);
        nuevoRol.setNombreRol("MODERADOR");

        EstadoUsuario nuevoEstado = new EstadoUsuario();
        nuevoEstado.setIdEstadoUsuario(2L);
        nuevoEstado.setNombreEstadoUsuario("BANEADO");

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setIdUsuario(1L);
        usuarioActualizado.setRol(nuevoRol);
        usuarioActualizado.setEstadoUsuario(nuevoEstado);

        given(usuarioRepository.findById(1L)).willReturn(Optional.of(usuarioMock));
        given(rolRepository.findById(2L)).willReturn(Optional.of(nuevoRol));
        given(estadoUsuarioRepository.findById(2L)).willReturn(Optional.of(nuevoEstado));
        given(usuarioRepository.save(any(Usuario.class))).willReturn(usuarioActualizado);

        // When
        UsuarioDTO result = usuarioService.actualizarRolYEstado(1L, 2L, 2L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.nombreRol()).isEqualTo("MODERADOR");
        assertThat(result.nombreEstado()).isEqualTo("BANEADO");

        verify(usuarioRepository, times(1)).findById(1L);
        verify(rolRepository, times(1)).findById(2L);
        verify(estadoUsuarioRepository, times(1)).findById(2L);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }
}
