package cl.videojuego.personaje_service.service;

import cl.videojuego.personaje_service.client.UsuarioClient;
import cl.videojuego.personaje_service.dto.PersonajeDTO;
import cl.videojuego.personaje_service.dto.PersonajeRegistroDTO;
import cl.videojuego.personaje_service.dto.UsuarioDTO;
import cl.videojuego.personaje_service.exception.UsuarioNoAptoException;
import cl.videojuego.personaje_service.model.ClasePersonaje;
import cl.videojuego.personaje_service.model.EstadoPersonaje;
import cl.videojuego.personaje_service.model.Personaje;
import cl.videojuego.personaje_service.repository.ClasePersonajeRepository;
import cl.videojuego.personaje_service.repository.EstadoPersonajeRepository;
import cl.videojuego.personaje_service.repository.PersonajeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class PersonajeServiceTest {

    @Mock
    private PersonajeRepository personajeRepository;

    @Mock
    private ClasePersonajeRepository clasePersonajeRepository;

    @Mock
    private EstadoPersonajeRepository estadoPersonajeRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private PersonajeService personajeService;

    private PersonajeRegistroDTO registroDTO;
    private UsuarioDTO usuarioDTO;
    private ClasePersonaje clasePersonaje;
    private EstadoPersonaje estadoPersonaje;
    private Personaje personaje;

    @BeforeEach
    void setUp() {
        registroDTO = new PersonajeRegistroDTO();
        registroDTO.setIdUsuario(1L);
        registroDTO.setIdClasePersonaje(1L);
        registroDTO.setIdEstadoPersonaje(1L);
        registroDTO.setNombre("HeroeTest");

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombreEstado("ACTIVO");
        usuarioDTO.setNombreRol("JUGADOR");

        clasePersonaje = new ClasePersonaje();
        clasePersonaje.setIdClasePersonaje(1L);

        estadoPersonaje = new EstadoPersonaje();
        estadoPersonaje.setIdEstadoPersonaje(1L);

        personaje = new Personaje();
        personaje.setIdPersonaje(1L);
        personaje.setNombre("HeroeTest");
        personaje.setNivel(1);
        personaje.setVida(100);
        personaje.setMana(50);
        personaje.setIdUsuario(1L);
        personaje.setClasePersonaje(clasePersonaje);
        personaje.setEstadoPersonaje(estadoPersonaje);
    }

    @Test
    @DisplayName("Debe listar todos los personajes correctamente")
    void shouldReturnAllPersonajes() {
        // Given
        given(personajeRepository.findAll()).willReturn(List.of(personaje));

        // When
        List<PersonajeDTO> resultado = personajeService.listarTodos();

        // Then
        assertThat(resultado).isNotNull().hasSize(1);
        assertThat(resultado.get(0).getNombre()).isEqualTo("HeroeTest");
        
        verify(personajeRepository).findAll();
        verifyNoMoreInteractions(personajeRepository);
    }

    @Test
    @DisplayName("Debe registrar personaje exitosamente cuando usuario y datos son validos")
    void shouldRegisterPersonajeSuccessfullyWhenValid() {
        // Given
        given(usuarioClient.buscarUsuarioPorId(1L)).willReturn(usuarioDTO);
        given(clasePersonajeRepository.findById(1L)).willReturn(Optional.of(clasePersonaje));
        given(estadoPersonajeRepository.findById(1L)).willReturn(Optional.of(estadoPersonaje));
        given(personajeRepository.save(any(Personaje.class))).willReturn(personaje);

        // When
        PersonajeDTO resultado = personajeService.registrar(registroDTO);

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("HeroeTest");
        assertThat(resultado.getNivel()).isEqualTo(1);
        
        verify(usuarioClient).buscarUsuarioPorId(1L);
        verify(clasePersonajeRepository).findById(1L);
        verify(estadoPersonajeRepository).findById(1L);
        verify(personajeRepository).save(any(Personaje.class));
    }

    @Test
    @DisplayName("Debe lanzar excepcion al registrar cuando el usuario esta baneado")
    void shouldThrowExceptionWhenRegisteringBannedUser() {
        // Given
        usuarioDTO.setNombreEstado("BANEADO");
        given(usuarioClient.buscarUsuarioPorId(1L)).willReturn(usuarioDTO);

        // When & Then
        assertThatThrownBy(() -> personajeService.registrar(registroDTO))
                .isInstanceOf(UsuarioNoAptoException.class)
                .hasMessage("No se puede crear personaje porque el usuario está baneado");

        verify(usuarioClient).buscarUsuarioPorId(1L);
        verifyNoInteractions(clasePersonajeRepository, estadoPersonajeRepository, personajeRepository);
    }

    @Test
    @DisplayName("Debe lanzar excepcion al registrar cuando el usuario esta suspendido")
    void shouldThrowExceptionWhenRegisteringSuspendedUser() {
        // Given
        usuarioDTO.setNombreEstado("SUSPENDIDO");
        given(usuarioClient.buscarUsuarioPorId(1L)).willReturn(usuarioDTO);

        // When & Then
        assertThatThrownBy(() -> personajeService.registrar(registroDTO))
                .isInstanceOf(UsuarioNoAptoException.class)
                .hasMessage("No se puede crear personaje porque el usuario está suspendido");

        verify(usuarioClient).buscarUsuarioPorId(1L);
        verifyNoInteractions(clasePersonajeRepository, estadoPersonajeRepository, personajeRepository);
    }

    @Test
    @DisplayName("Debe lanzar excepcion al registrar cuando el usuario es admin")
    void shouldThrowExceptionWhenRegisteringAdminUser() {
        // Given
        usuarioDTO.setNombreRol("ADMIN");
        given(usuarioClient.buscarUsuarioPorId(1L)).willReturn(usuarioDTO);

        // When & Then
        assertThatThrownBy(() -> personajeService.registrar(registroDTO))
                .isInstanceOf(UsuarioNoAptoException.class)
                .hasMessage("No se puede crear personaje porque los administradores no juegan como personajes");

        verify(usuarioClient).buscarUsuarioPorId(1L);
        verifyNoInteractions(clasePersonajeRepository, estadoPersonajeRepository, personajeRepository);
    }

    @Test
    @DisplayName("Debe lanzar excepcion al registrar cuando clase de personaje no existe")
    void shouldThrowExceptionWhenClasePersonajeNotFound() {
        // Given
        given(usuarioClient.buscarUsuarioPorId(1L)).willReturn(usuarioDTO);
        given(clasePersonajeRepository.findById(1L)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> personajeService.registrar(registroDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Clase no encontrada");

        verify(usuarioClient).buscarUsuarioPorId(1L);
        verify(clasePersonajeRepository).findById(1L);
        verifyNoInteractions(estadoPersonajeRepository, personajeRepository);
    }

    @Test
    @DisplayName("Debe lanzar excepcion al registrar cuando estado de personaje no existe")
    void shouldThrowExceptionWhenEstadoPersonajeNotFound() {
        // Given
        given(usuarioClient.buscarUsuarioPorId(1L)).willReturn(usuarioDTO);
        given(clasePersonajeRepository.findById(1L)).willReturn(Optional.of(clasePersonaje));
        given(estadoPersonajeRepository.findById(1L)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> personajeService.registrar(registroDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Estado no encontrado");

        verify(usuarioClient).buscarUsuarioPorId(1L);
        verify(clasePersonajeRepository).findById(1L);
        verify(estadoPersonajeRepository).findById(1L);
        verifyNoInteractions(personajeRepository);
    }

    @Test
    @DisplayName("Debe listar personajes por id de usuario")
    void shouldReturnPersonajesByUsuarioId() {
        // Given
        given(personajeRepository.findByIdUsuario(1L)).willReturn(List.of(personaje));

        // When
        List<PersonajeDTO> resultado = personajeService.listarPorUsuario(1L);

        // Then
        assertThat(resultado).isNotNull().hasSize(1);
        assertThat(resultado.get(0).getIdPersonaje()).isEqualTo(1L);
        
        verify(personajeRepository).findByIdUsuario(1L);
    }

    @Test
    @DisplayName("Debe listar personajes por nivel")
    void shouldReturnPersonajesByNivel() {
        // Given
        given(personajeRepository.findByNivel(1)).willReturn(List.of(personaje));

        // When
        List<PersonajeDTO> resultado = personajeService.listarPorNivel(1);

        // Then
        assertThat(resultado).isNotNull().hasSize(1);
        assertThat(resultado.get(0).getNivel()).isEqualTo(1);
        
        verify(personajeRepository).findByNivel(1);
    }

    @Test
    @DisplayName("Debe listar personajes por id de clase")
    void shouldReturnPersonajesByClaseId() {
        // Given
        given(personajeRepository.findByClasePersonaje_IdClasePersonaje(1L)).willReturn(List.of(personaje));

        // When
        List<PersonajeDTO> resultado = personajeService.listarPorClase(1L);

        // Then
        assertThat(resultado).isNotNull().hasSize(1);
        
        verify(personajeRepository).findByClasePersonaje_IdClasePersonaje(1L);
    }

    @Test
    @DisplayName("Debe listar personajes por id de estado")
    void shouldReturnPersonajesByEstadoId() {
        // Given
        given(personajeRepository.findByEstadoPersonaje_IdEstadoPersonaje(1L)).willReturn(List.of(personaje));

        // When
        List<PersonajeDTO> resultado = personajeService.listarPorEstado(1L);

        // Then
        assertThat(resultado).isNotNull().hasSize(1);
        
        verify(personajeRepository).findByEstadoPersonaje_IdEstadoPersonaje(1L);
    }

    @Test
    @DisplayName("Debe buscar personaje por id exitosamente")
    void shouldFindPersonajeById() {
        // Given
        given(personajeRepository.findById(1L)).willReturn(Optional.of(personaje));

        // When
        PersonajeDTO resultado = personajeService.buscarPorId(1L);

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdPersonaje()).isEqualTo(1L);
        
        verify(personajeRepository).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepcion cuando se busca personaje por id y no existe")
    void shouldThrowExceptionWhenPersonajeNotFoundById() {
        // Given
        given(personajeRepository.findById(1L)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> personajeService.buscarPorId(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Personaje no encontrado");

        verify(personajeRepository).findById(1L);
    }
}
