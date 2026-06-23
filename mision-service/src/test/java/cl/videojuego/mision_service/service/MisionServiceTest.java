package cl.videojuego.mision_service.service;

import cl.videojuego.mision_service.dto.MisionDTO;
import cl.videojuego.mision_service.dto.MisionRegistroDTO;
import cl.videojuego.mision_service.model.EstadoMision;
import cl.videojuego.mision_service.model.Mision;
import cl.videojuego.mision_service.model.TipoMision;
import cl.videojuego.mision_service.repository.EstadoMisionRepository;
import cl.videojuego.mision_service.repository.MisionRepository;
import cl.videojuego.mision_service.repository.TipoMisionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MisionServiceTest {

    @Mock
    private MisionRepository misionRepository;

    @Mock
    private TipoMisionRepository tipoMisionRepository;

    @Mock
    private EstadoMisionRepository estadoMisionRepository;

    @InjectMocks
    private MisionService misionService;

    private Mision misionMock;
    private TipoMision tipoMisionMock;
    private EstadoMision estadoMisionMock;
    private MisionRegistroDTO registroDTOMock;

    @BeforeEach
    void setUp() {
        tipoMisionMock = new TipoMision();
        tipoMisionMock.setIdTipoMision(1L);
        tipoMisionMock.setNombreTipo("Historia");

        estadoMisionMock = new EstadoMision();
        estadoMisionMock.setIdEstadoMision(1L);
        estadoMisionMock.setNombreEstado("Activa");

        misionMock = new Mision();
        misionMock.setIdMision(100L);
        misionMock.setNombreMision("Rescatar al príncipe");
        misionMock.setDescripcion("Misión principal");
        misionMock.setRecompensaExperiencia(500);
        misionMock.setRecompensaMonedas(100);
        misionMock.setNivelMinimo(5);
        misionMock.setTipoMision(tipoMisionMock);
        misionMock.setEstadoMision(estadoMisionMock);

        registroDTOMock = new MisionRegistroDTO();
        registroDTOMock.setNombreMision("Rescatar al príncipe");
        registroDTOMock.setDescripcion("Misión principal");
        registroDTOMock.setRecompensaExperiencia(500);
        registroDTOMock.setRecompensaMonedas(100);
        registroDTOMock.setNivelMinimo(5);
        registroDTOMock.setIdTipoMision(1L);
        registroDTOMock.setIdEstadoMision(1L);
    }

    @Test
    @DisplayName("Debería listar todas las misiones correctamente")
    void deberiaListarTodasLasMisiones() {
        // Given
        when(misionRepository.findAll()).thenReturn(Arrays.asList(misionMock));

        // When
        List<MisionDTO> resultado = misionService.listarTodas();

        // Then
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(misionMock.getNombreMision(), resultado.get(0).getNombreMision());
        verify(misionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería retornar una lista vacía cuando no hay misiones registradas")
    void deberiaRetornarListaVaciaAlListarTodas() {
        // Given
        when(misionRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<MisionDTO> resultado = misionService.listarTodas();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(misionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería registrar una nueva misión exitosamente")
    void deberiaRegistrarNuevaMision() {
        // Given
        when(tipoMisionRepository.findById(1L)).thenReturn(Optional.of(tipoMisionMock));
        when(estadoMisionRepository.findById(1L)).thenReturn(Optional.of(estadoMisionMock));
        when(misionRepository.save(any(Mision.class))).thenReturn(misionMock);

        // When
        MisionDTO resultado = misionService.registrar(registroDTOMock);

        // Then
        assertNotNull(resultado);
        assertEquals("Rescatar al príncipe", resultado.getNombreMision());
        assertEquals("Historia", resultado.getNombreTipo());
        assertEquals("Activa", resultado.getNombreEstado());
        
        verify(tipoMisionRepository, times(1)).findById(1L);
        verify(estadoMisionRepository, times(1)).findById(1L);
        verify(misionRepository, times(1)).save(any(Mision.class));
    }

    @Test
    @DisplayName("Debería lanzar excepción al registrar con tipo de misión inexistente")
    void deberiaLanzarExcepcionAlRegistrarTipoMisionNoEncontrado() {
        // Given
        when(tipoMisionRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            misionService.registrar(registroDTOMock);
        });

        // Then
        assertEquals("Tipo de misión no encontrado", exception.getMessage());
        verify(tipoMisionRepository, times(1)).findById(1L);
        verify(estadoMisionRepository, never()).findById(anyLong());
        verify(misionRepository, never()).save(any(Mision.class));
    }

    @Test
    @DisplayName("Debería lanzar excepción al registrar con estado de misión inexistente")
    void deberiaLanzarExcepcionAlRegistrarEstadoMisionNoEncontrado() {
        // Given
        when(tipoMisionRepository.findById(1L)).thenReturn(Optional.of(tipoMisionMock));
        when(estadoMisionRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            misionService.registrar(registroDTOMock);
        });

        // Then
        assertEquals("Estado de misión no encontrado", exception.getMessage());
        verify(tipoMisionRepository, times(1)).findById(1L);
        verify(estadoMisionRepository, times(1)).findById(1L);
        verify(misionRepository, never()).save(any(Mision.class));
    }

    @Test
    @DisplayName("Debería listar misiones por tipo exitosamente")
    void deberiaListarMisionesPorTipo() {
        // Given
        Long idTipo = 1L;
        when(misionRepository.findByTipoMision_IdTipoMision(idTipo)).thenReturn(Arrays.asList(misionMock));

        // When
        List<MisionDTO> resultado = misionService.listarPorTipo(idTipo);

        // Then
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Historia", resultado.get(0).getNombreTipo());
        verify(misionRepository, times(1)).findByTipoMision_IdTipoMision(idTipo);
    }

    @Test
    @DisplayName("Debería listar misiones por estado exitosamente")
    void deberiaListarMisionesPorEstado() {
        // Given
        Long idEstado = 1L;
        when(misionRepository.findByEstadoMision_IdEstadoMision(idEstado)).thenReturn(Arrays.asList(misionMock));

        // When
        List<MisionDTO> resultado = misionService.listarPorEstado(idEstado);

        // Then
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Activa", resultado.get(0).getNombreEstado());
        verify(misionRepository, times(1)).findByEstadoMision_IdEstadoMision(idEstado);
    }

    @Test
    @DisplayName("Debería listar misiones por nivel mínimo exitosamente")
    void deberiaListarMisionesPorNivelMinimo() {
        // Given
        Integer nivelMinimo = 5;
        when(misionRepository.findByNivelMinimo(nivelMinimo)).thenReturn(Arrays.asList(misionMock));

        // When
        List<MisionDTO> resultado = misionService.listarPorNivel(nivelMinimo);

        // Then
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(nivelMinimo, resultado.get(0).getNivelMinimo());
        verify(misionRepository, times(1)).findByNivelMinimo(nivelMinimo);
    }

    @Test
    @DisplayName("Debería buscar misiones por nombre parcialmente de forma exitosa")
    void deberiaBuscarMisionesPorNombre() {
        // Given
        String nombreBuscado = "príncipe";
        when(misionRepository.findByNombreMisionContainingIgnoreCase(nombreBuscado)).thenReturn(Arrays.asList(misionMock));

        // When
        List<MisionDTO> resultado = misionService.buscarPorNombre(nombreBuscado);

        // Then
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getNombreMision().contains("príncipe"));
        verify(misionRepository, times(1)).findByNombreMisionContainingIgnoreCase(nombreBuscado);
    }

    @Test
    @DisplayName("Debería buscar misiones por recompensa en monedas menor o igual exitosamente")
    void deberiaBuscarMisionesPorRecompensaMonedas() {
        // Given
        Integer recompensaMaxima = 150;
        when(misionRepository.findByRecompensaMonedasLessThanEqual(recompensaMaxima)).thenReturn(Arrays.asList(misionMock));

        // When
        List<MisionDTO> resultado = misionService.buscarPorRecompensa(recompensaMaxima);

        // Then
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getRecompensaMonedas() <= recompensaMaxima);
        verify(misionRepository, times(1)).findByRecompensaMonedasLessThanEqual(recompensaMaxima);
    }
}
