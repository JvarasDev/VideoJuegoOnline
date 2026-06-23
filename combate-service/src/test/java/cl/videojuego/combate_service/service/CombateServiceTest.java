package cl.videojuego.combate_service.service;

import cl.videojuego.combate_service.client.PersonajeClient;
import cl.videojuego.combate_service.dto.CombateDTO;
import cl.videojuego.combate_service.dto.CombateRegistroDTO;
import cl.videojuego.combate_service.dto.PersonajeDTO;
import cl.videojuego.combate_service.exception.ResultadoCombateInvalidoException;
import cl.videojuego.combate_service.mapper.CombateMapper;
import cl.videojuego.combate_service.model.Combate;
import cl.videojuego.combate_service.model.EstadoCombate;
import cl.videojuego.combate_service.model.TipoCombate;
import cl.videojuego.combate_service.repository.CombateRepository;
import cl.videojuego.combate_service.repository.EstadoCombateRepository;
import cl.videojuego.combate_service.repository.TipoCombateRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CombateServiceTest {

    @Mock
    private CombateRepository combateRepository;

    @Mock
    private TipoCombateRepository tipoCombateRepository;

    @Mock
    private EstadoCombateRepository estadoCombateRepository;

    @Mock
    private PersonajeClient personajeClient;

    @InjectMocks
    private CombateService combateService;

    private MockedStatic<CombateMapper> mockedMapper;

    @BeforeEach
    void setUp() {
        mockedMapper = mockStatic(CombateMapper.class);
    }

    @AfterEach
    void tearDown() {
        mockedMapper.close();
    }

    @Test
    @DisplayName("Debe registrar un combate exitosamente cuando los datos son válidos")
    void registrar_ConDatosValidos_DeberiaRetornarCombateDTO() {
        // Given
        Long idAtacante = 1L;
        Long idDefensor = 2L;
        Long idGanador = 1L;
        
        CombateRegistroDTO registroDTO = new CombateRegistroDTO();
        registroDTO.setIdPersonajeAtacante(idAtacante);
        registroDTO.setIdPersonajeDefensor(idDefensor);
        registroDTO.setIdGanador(idGanador);
        registroDTO.setIdTipoCombate(1L);
        registroDTO.setIdEstadoCombate(1L);
        registroDTO.setExperienciaGanada(100);
        registroDTO.setMonedasGanadas(50);
        registroDTO.setDuracionSegundos(120);

        PersonajeDTO atacante = new PersonajeDTO();
        PersonajeDTO defensor = new PersonajeDTO();
        PersonajeDTO ganador = atacante;

        TipoCombate tipoCombate = new TipoCombate();
        EstadoCombate estadoCombate = new EstadoCombate();
        Combate combateGuardado = new Combate();
        CombateDTO combateDTOEsperado = new CombateDTO();

        when(personajeClient.buscarPersonajePorId(idAtacante)).thenReturn(atacante);
        when(personajeClient.buscarPersonajePorId(idDefensor)).thenReturn(defensor);
        // El idGanador es el mismo que el atacante, por lo que mockito ya lo tiene contemplado o devolverá atacante por ser mock lenient/estricto dependiendo de la version, pero como es el mismo idAtacante = idGanador, devolverá atacante.
        
        when(tipoCombateRepository.findById(1L)).thenReturn(Optional.of(tipoCombate));
        when(estadoCombateRepository.findById(1L)).thenReturn(Optional.of(estadoCombate));
        when(combateRepository.save(any(Combate.class))).thenReturn(combateGuardado);
        
        mockedMapper.when(() -> CombateMapper.toDTO(combateGuardado, atacante, defensor, ganador))
                .thenReturn(combateDTOEsperado);

        // When
        CombateDTO resultado = combateService.registrar(registroDTO);

        // Then
        assertNotNull(resultado);
        assertEquals(combateDTOEsperado, resultado);
        verify(combateRepository, times(1)).save(any(Combate.class));
        verify(personajeClient, atLeast(2)).buscarPersonajePorId(anyLong());
        verify(tipoCombateRepository, times(1)).findById(1L);
        verify(estadoCombateRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar ResultadoCombateInvalidoException cuando el ganador no es ni atacante ni defensor")
    void registrar_ConGanadorInvalido_DeberiaLanzarException() {
        // Given
        CombateRegistroDTO registroDTO = new CombateRegistroDTO();
        registroDTO.setIdPersonajeAtacante(1L);
        registroDTO.setIdPersonajeDefensor(2L);
        registroDTO.setIdGanador(3L); // Ganador inválido

        PersonajeDTO atacante = new PersonajeDTO();
        PersonajeDTO defensor = new PersonajeDTO();
        PersonajeDTO ganadorInvalido = new PersonajeDTO();

        when(personajeClient.buscarPersonajePorId(1L)).thenReturn(atacante);
        when(personajeClient.buscarPersonajePorId(2L)).thenReturn(defensor);
        when(personajeClient.buscarPersonajePorId(3L)).thenReturn(ganadorInvalido);

        // When
        ResultadoCombateInvalidoException exception = assertThrows(
                ResultadoCombateInvalidoException.class,
                () -> combateService.registrar(registroDTO)
        );

        // Then
        assertEquals("El ganador debe ser el atacante o el defensor", exception.getMessage());
        verify(combateRepository, never()).save(any(Combate.class));
    }

    @Test
    @DisplayName("Debe lanzar RuntimeException cuando el tipo de combate no existe")
    void registrar_ConTipoCombateNoEncontrado_DeberiaLanzarException() {
        // Given
        CombateRegistroDTO registroDTO = new CombateRegistroDTO();
        registroDTO.setIdPersonajeAtacante(1L);
        registroDTO.setIdPersonajeDefensor(2L);
        registroDTO.setIdGanador(1L);
        registroDTO.setIdTipoCombate(99L); // Tipo no existe

        when(personajeClient.buscarPersonajePorId(anyLong())).thenReturn(new PersonajeDTO());
        when(tipoCombateRepository.findById(99L)).thenReturn(Optional.empty());

        // When
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> combateService.registrar(registroDTO)
        );

        // Then
        assertEquals("Tipo de combate no encontrado", exception.getMessage());
        verify(estadoCombateRepository, never()).findById(anyLong());
        verify(combateRepository, never()).save(any(Combate.class));
    }

    @Test
    @DisplayName("Debe lanzar RuntimeException cuando el estado del combate no existe")
    void registrar_ConEstadoCombateNoEncontrado_DeberiaLanzarException() {
        // Given
        CombateRegistroDTO registroDTO = new CombateRegistroDTO();
        registroDTO.setIdPersonajeAtacante(1L);
        registroDTO.setIdPersonajeDefensor(2L);
        registroDTO.setIdGanador(1L);
        registroDTO.setIdTipoCombate(1L);
        registroDTO.setIdEstadoCombate(99L); // Estado no existe

        when(personajeClient.buscarPersonajePorId(anyLong())).thenReturn(new PersonajeDTO());
        when(tipoCombateRepository.findById(1L)).thenReturn(Optional.of(new TipoCombate()));
        when(estadoCombateRepository.findById(99L)).thenReturn(Optional.empty());

        // When
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> combateService.registrar(registroDTO)
        );

        // Then
        assertEquals("Estado de combate no encontrado", exception.getMessage());
        verify(combateRepository, never()).save(any(Combate.class));
    }

    @Test
    @DisplayName("Debe listar todos los combates mapeados a DTO")
    void listarTodos_DeberiaRetornarListaDeCombates() {
        // Given
        Combate combate = new Combate();
        combate.setIdPersonajeAtacante(1L);
        combate.setIdPersonajeDefensor(2L);
        combate.setIdGanador(1L);
        
        List<Combate> combates = Collections.singletonList(combate);
        CombateDTO combateDTO = new CombateDTO();

        when(combateRepository.findAll()).thenReturn(combates);
        when(personajeClient.buscarPersonajePorId(anyLong())).thenReturn(new PersonajeDTO());
        mockedMapper.when(() -> CombateMapper.toDTO(any(Combate.class), any(), any(), any()))
                .thenReturn(combateDTO);

        // When
        List<CombateDTO> resultado = combateService.listarTodos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(combateDTO, resultado.get(0));
        verify(combateRepository, times(1)).findAll();
        verify(personajeClient, times(3)).buscarPersonajePorId(anyLong()); // atacante, defensor, ganador
    }

    @Test
    @DisplayName("Debe listar los combates filtrados por atacante")
    void listarPorAtacante_DeberiaRetornarListaDeCombates() {
        // Given
        Long idAtacante = 1L;
        Combate combate = new Combate();
        combate.setIdPersonajeAtacante(idAtacante);
        combate.setIdPersonajeDefensor(2L);
        combate.setIdGanador(1L);

        when(combateRepository.findByIdPersonajeAtacante(idAtacante)).thenReturn(Collections.singletonList(combate));
        when(personajeClient.buscarPersonajePorId(anyLong())).thenReturn(new PersonajeDTO());
        mockedMapper.when(() -> CombateMapper.toDTO(any(Combate.class), any(), any(), any()))
                .thenReturn(new CombateDTO());

        // When
        List<CombateDTO> resultado = combateService.listarPorAtacante(idAtacante);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(combateRepository, times(1)).findByIdPersonajeAtacante(idAtacante);
        verify(personajeClient, times(3)).buscarPersonajePorId(anyLong());
    }

    @Test
    @DisplayName("Debe listar los combates filtrados por defensor")
    void listarPorDefensor_DeberiaRetornarListaDeCombates() {
        // Given
        Long idDefensor = 2L;
        Combate combate = new Combate();
        combate.setIdPersonajeAtacante(1L);
        combate.setIdPersonajeDefensor(idDefensor);
        combate.setIdGanador(2L);

        when(combateRepository.findByIdPersonajeDefensor(idDefensor)).thenReturn(Collections.singletonList(combate));
        when(personajeClient.buscarPersonajePorId(anyLong())).thenReturn(new PersonajeDTO());
        mockedMapper.when(() -> CombateMapper.toDTO(any(Combate.class), any(), any(), any()))
                .thenReturn(new CombateDTO());

        // When
        List<CombateDTO> resultado = combateService.listarPorDefensor(idDefensor);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(combateRepository, times(1)).findByIdPersonajeDefensor(idDefensor);
        verify(personajeClient, times(3)).buscarPersonajePorId(anyLong());
    }

    @Test
    @DisplayName("Debe listar los combates filtrados por ganador")
    void listarPorGanador_DeberiaRetornarListaDeCombates() {
        // Given
        Long idGanador = 1L;
        Combate combate = new Combate();
        combate.setIdPersonajeAtacante(1L);
        combate.setIdPersonajeDefensor(2L);
        combate.setIdGanador(idGanador);

        when(combateRepository.findByIdGanador(idGanador)).thenReturn(Collections.singletonList(combate));
        when(personajeClient.buscarPersonajePorId(anyLong())).thenReturn(new PersonajeDTO());
        mockedMapper.when(() -> CombateMapper.toDTO(any(Combate.class), any(), any(), any()))
                .thenReturn(new CombateDTO());

        // When
        List<CombateDTO> resultado = combateService.listarPorGanador(idGanador);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(combateRepository, times(1)).findByIdGanador(idGanador);
        verify(personajeClient, times(3)).buscarPersonajePorId(anyLong());
    }

    @Test
    @DisplayName("Debe listar los combates filtrados por tipo de combate")
    void listarPorTipo_DeberiaRetornarListaDeCombates() {
        // Given
        Long idTipoCombate = 1L;
        Combate combate = new Combate();
        combate.setIdPersonajeAtacante(1L);
        combate.setIdPersonajeDefensor(2L);
        combate.setIdGanador(1L);

        when(combateRepository.findByTipoCombate_IdTipoCombate(idTipoCombate)).thenReturn(Collections.singletonList(combate));
        when(personajeClient.buscarPersonajePorId(anyLong())).thenReturn(new PersonajeDTO());
        mockedMapper.when(() -> CombateMapper.toDTO(any(Combate.class), any(), any(), any()))
                .thenReturn(new CombateDTO());

        // When
        List<CombateDTO> resultado = combateService.listarPorTipo(idTipoCombate);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(combateRepository, times(1)).findByTipoCombate_IdTipoCombate(idTipoCombate);
        verify(personajeClient, times(3)).buscarPersonajePorId(anyLong());
    }

    @Test
    @DisplayName("Debe listar los combates filtrados por estado del combate")
    void listarPorEstado_DeberiaRetornarListaDeCombates() {
        // Given
        Long idEstadoCombate = 1L;
        Combate combate = new Combate();
        combate.setIdPersonajeAtacante(1L);
        combate.setIdPersonajeDefensor(2L);
        combate.setIdGanador(1L);

        when(combateRepository.findByEstadoCombate_IdEstadoCombate(idEstadoCombate)).thenReturn(Collections.singletonList(combate));
        when(personajeClient.buscarPersonajePorId(anyLong())).thenReturn(new PersonajeDTO());
        mockedMapper.when(() -> CombateMapper.toDTO(any(Combate.class), any(), any(), any()))
                .thenReturn(new CombateDTO());

        // When
        List<CombateDTO> resultado = combateService.listarPorEstado(idEstadoCombate);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(combateRepository, times(1)).findByEstadoCombate_IdEstadoCombate(idEstadoCombate);
        verify(personajeClient, times(3)).buscarPersonajePorId(anyLong());
    }
}
