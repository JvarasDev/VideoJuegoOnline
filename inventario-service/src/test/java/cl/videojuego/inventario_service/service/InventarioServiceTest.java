package cl.videojuego.inventario_service.service;

import cl.videojuego.inventario_service.client.PersonajeClient;
import cl.videojuego.inventario_service.dto.InventarioDTO;
import cl.videojuego.inventario_service.dto.InventarioRegistroDTO;
import cl.videojuego.inventario_service.dto.PersonajeDTO;
import cl.videojuego.inventario_service.model.EstadoInventario;
import cl.videojuego.inventario_service.model.Inventario;
import cl.videojuego.inventario_service.repository.EstadoInventarioRepository;
import cl.videojuego.inventario_service.repository.InventarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private EstadoInventarioRepository estadoInventarioRepository;

    @Mock
    private PersonajeClient personajeClient;

    @InjectMocks
    private InventarioService inventarioService;

    @Test
    void listarTodos_DebeRetornarListaDeInventariosDTO() {
        // Given
        EstadoInventario estado = new EstadoInventario();
        estado.setIdEstadoInventario(1L);
        estado.setNombreEstado("ACTIVO");

        Inventario inventario = new Inventario();
        // Setters based on the properties seen in the Service
        inventario.setIdPersonaje(10L);
        inventario.setCapacidadMaxima(20);
        inventario.setEspaciosUsados(5);
        inventario.setFechaCreacion(LocalDate.now());
        inventario.setEstadoInventario(estado);

        PersonajeDTO personajeDTO = new PersonajeDTO();

        when(inventarioRepository.findAll()).thenReturn(List.of(inventario));
        when(personajeClient.buscarPersonajePorId(10L)).thenReturn(personajeDTO);

        // When
        List<InventarioDTO> result = inventarioService.listarTodos();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(inventarioRepository, times(1)).findAll();
        verify(personajeClient, times(1)).buscarPersonajePorId(10L);
    }

    @Test
    void registrar_CuandoEstadoExiste_DebeGuardarYRetornarInventarioDTO() {
        // Given
        InventarioRegistroDTO registroDTO = new InventarioRegistroDTO();
        registroDTO.setIdPersonaje(10L);
        registroDTO.setIdEstadoInventario(1L);
        registroDTO.setCapacidadMaxima(50);

        PersonajeDTO personajeDTO = new PersonajeDTO();

        EstadoInventario estadoInventario = new EstadoInventario();

        Inventario inventarioGuardado = new Inventario();
        inventarioGuardado.setIdPersonaje(10L);
        inventarioGuardado.setCapacidadMaxima(50);
        inventarioGuardado.setEspaciosUsados(0);
        inventarioGuardado.setEstadoInventario(estadoInventario);
        inventarioGuardado.setFechaCreacion(LocalDate.now());

        when(personajeClient.buscarPersonajePorId(10L)).thenReturn(personajeDTO);
        when(estadoInventarioRepository.findById(1L)).thenReturn(Optional.of(estadoInventario));
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventarioGuardado);

        // When
        InventarioDTO result = inventarioService.registrar(registroDTO);

        // Then
        assertNotNull(result);
        verify(personajeClient, times(1)).buscarPersonajePorId(10L);
        verify(estadoInventarioRepository, times(1)).findById(1L);
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }

    @Test
    void registrar_CuandoEstadoNoExiste_DebeLanzarExcepcion() {
        // Given
        InventarioRegistroDTO registroDTO = new InventarioRegistroDTO();
        registroDTO.setIdPersonaje(10L);
        registroDTO.setIdEstadoInventario(99L);
        registroDTO.setCapacidadMaxima(50);

        PersonajeDTO personajeDTO = new PersonajeDTO();

        when(personajeClient.buscarPersonajePorId(10L)).thenReturn(personajeDTO);
        when(estadoInventarioRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            inventarioService.registrar(registroDTO);
        });

        assertEquals("Estado de inventario no encontrado", exception.getMessage());
        verify(personajeClient, times(1)).buscarPersonajePorId(10L);
        verify(estadoInventarioRepository, times(1)).findById(99L);
        verify(inventarioRepository, never()).save(any(Inventario.class));
    }

    @Test
    void listarPorPersonaje_DebeRetornarListaDeInventariosDTOPorPersonaje() {
        // Given
        Long idPersonaje = 10L;
        EstadoInventario estado = new EstadoInventario();
        estado.setIdEstadoInventario(1L);
        estado.setNombreEstado("ACTIVO");

        Inventario inventario = new Inventario();
        inventario.setIdPersonaje(idPersonaje);
        inventario.setEstadoInventario(estado);
        
        PersonajeDTO personajeDTO = new PersonajeDTO();

        when(inventarioRepository.findByIdPersonaje(idPersonaje)).thenReturn(List.of(inventario));
        when(personajeClient.buscarPersonajePorId(idPersonaje)).thenReturn(personajeDTO);

        // When
        List<InventarioDTO> result = inventarioService.listarPorPersonaje(idPersonaje);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(inventarioRepository, times(1)).findByIdPersonaje(idPersonaje);
        verify(personajeClient, times(1)).buscarPersonajePorId(idPersonaje);
    }

    @Test
    void listarPorEstado_DebeRetornarListaDeInventariosDTOPorEstado() {
        // Given
        Long idEstado = 1L;
        EstadoInventario estado = new EstadoInventario();
        estado.setIdEstadoInventario(idEstado);
        estado.setNombreEstado("ACTIVO");

        Inventario inventario = new Inventario();
        inventario.setIdPersonaje(10L);
        inventario.setEstadoInventario(estado);
        
        PersonajeDTO personajeDTO = new PersonajeDTO();

        when(inventarioRepository.findByEstadoInventario_IdEstadoInventario(idEstado)).thenReturn(List.of(inventario));
        when(personajeClient.buscarPersonajePorId(10L)).thenReturn(personajeDTO);

        // When
        List<InventarioDTO> result = inventarioService.listarPorEstado(idEstado);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(inventarioRepository, times(1)).findByEstadoInventario_IdEstadoInventario(idEstado);
        verify(personajeClient, times(1)).buscarPersonajePorId(10L);
    }

    @Test
    void buscarPorId_CuandoInventarioExiste_DebeRetornarInventarioDTO() {
        // Given
        Long idInventario = 1L;
        EstadoInventario estado = new EstadoInventario();
        estado.setIdEstadoInventario(1L);
        estado.setNombreEstado("ACTIVO");

        Inventario inventario = new Inventario();
        inventario.setIdPersonaje(10L);
        inventario.setEstadoInventario(estado);
        
        PersonajeDTO personajeDTO = new PersonajeDTO();

        when(inventarioRepository.findById(idInventario)).thenReturn(Optional.of(inventario));
        when(personajeClient.buscarPersonajePorId(10L)).thenReturn(personajeDTO);

        // When
        InventarioDTO result = inventarioService.buscarPorId(idInventario);

        // Then
        assertNotNull(result);
        verify(inventarioRepository, times(1)).findById(idInventario);
        verify(personajeClient, times(1)).buscarPersonajePorId(10L);
    }

    @Test
    void buscarPorId_CuandoInventarioNoExiste_DebeLanzarExcepcion() {
        // Given
        Long idInventario = 99L;

        when(inventarioRepository.findById(idInventario)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            inventarioService.buscarPorId(idInventario);
        });

        assertEquals("Inventario no encontrado", exception.getMessage());
        verify(inventarioRepository, times(1)).findById(idInventario);
        verify(personajeClient, never()).buscarPersonajePorId(any());
    }
}
