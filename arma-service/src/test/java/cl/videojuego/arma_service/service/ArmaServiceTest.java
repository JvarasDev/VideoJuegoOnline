package cl.videojuego.arma_service.service;

import cl.videojuego.arma_service.dto.ArmaDTO;
import cl.videojuego.arma_service.dto.ArmaRegistroDTO;
import cl.videojuego.arma_service.model.Arma;
import cl.videojuego.arma_service.model.RarezaArma;
import cl.videojuego.arma_service.model.TipoArma;
import cl.videojuego.arma_service.repository.ArmaRepository;
import cl.videojuego.arma_service.repository.RarezaArmaRepository;
import cl.videojuego.arma_service.repository.TipoArmaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArmaServiceTest {

    @Mock
    private ArmaRepository armaRepository;

    @Mock
    private TipoArmaRepository tipoArmaRepository;

    @Mock
    private RarezaArmaRepository rarezaArmaRepository;

    @InjectMocks
    private ArmaService armaService;

    private Arma arma;
    private TipoArma tipoArma;
    private RarezaArma rarezaArma;
    private ArmaRegistroDTO armaRegistroDTO;

    @BeforeEach
    void setUp() {
        // Configuración de TipoArma
        tipoArma = new TipoArma();
        tipoArma.setIdTipoArma(1L);
        tipoArma.setNombreTipo("Espada");
        tipoArma.setDescripcion("Arma cuerpo a cuerpo");

        // Configuración de RarezaArma
        rarezaArma = new RarezaArma();
        rarezaArma.setIdRarezaArma(1L);
        rarezaArma.setNombreRareza("Épica");
        rarezaArma.setMultiplicadorDanio(1.5);
        rarezaArma.setDescripcion("Arma muy rara");

        // Configuración de Arma
        arma = new Arma();
        arma.setIdArma(1L);
        arma.setNombreArma("Espada de Fuego");
        arma.setDanio(100);
        arma.setNivelMinimo(10);
        arma.setPrecio(500);
        arma.setTipoArma(tipoArma);
        arma.setRarezaArma(rarezaArma);

        // Configuración de ArmaRegistroDTO
        armaRegistroDTO = new ArmaRegistroDTO();
        armaRegistroDTO.setNombreArma("Espada de Fuego");
        armaRegistroDTO.setDanio(100);
        armaRegistroDTO.setNivelMinimo(10);
        armaRegistroDTO.setPrecio(500);
        armaRegistroDTO.setIdTipoArma(1L);
        armaRegistroDTO.setIdRarezaArma(1L);
    }

    @Test
    void listarTodas_debeRetornarListaDeArmas() {
        // Given
        when(armaRepository.findAll()).thenReturn(List.of(arma));

        // When
        List<ArmaDTO> resultado = armaService.listarTodas();

        // Then
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Espada de Fuego", resultado.get(0).getNombreArma());
        verify(armaRepository, times(1)).findAll();
    }

    @Test
    void registrar_conDatosValidos_debeRetornarArmaRegistrada() {
        // Given
        when(tipoArmaRepository.findById(1L)).thenReturn(Optional.of(tipoArma));
        when(rarezaArmaRepository.findById(1L)).thenReturn(Optional.of(rarezaArma));
        when(armaRepository.save(any(Arma.class))).thenReturn(arma);

        // When
        ArmaDTO resultado = armaService.registrar(armaRegistroDTO);

        // Then
        assertNotNull(resultado);
        assertEquals("Espada de Fuego", resultado.getNombreArma());
        assertEquals("Espada", resultado.getNombreTipo());
        assertEquals("Épica", resultado.getNombreRareza());
        verify(tipoArmaRepository, times(1)).findById(1L);
        verify(rarezaArmaRepository, times(1)).findById(1L);
        verify(armaRepository, times(1)).save(any(Arma.class));
    }

    @Test
    void registrar_conTipoArmaNoEncontrado_debeLanzarExcepcion() {
        // Given
        when(tipoArmaRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            armaService.registrar(armaRegistroDTO);
        });

        assertEquals("Tipo de arma no encontrado", exception.getMessage());
        verify(tipoArmaRepository, times(1)).findById(1L);
        verify(rarezaArmaRepository, never()).findById(anyLong());
        verify(armaRepository, never()).save(any(Arma.class));
    }

    @Test
    void registrar_conRarezaArmaNoEncontrada_debeLanzarExcepcion() {
        // Given
        when(tipoArmaRepository.findById(1L)).thenReturn(Optional.of(tipoArma));
        when(rarezaArmaRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            armaService.registrar(armaRegistroDTO);
        });

        assertEquals("Rareza no encontrada", exception.getMessage());
        verify(tipoArmaRepository, times(1)).findById(1L);
        verify(rarezaArmaRepository, times(1)).findById(1L);
        verify(armaRepository, never()).save(any(Arma.class));
    }

    @Test
    void listarPorTipo_debeRetornarListaDeArmas() {
        // Given
        Long idTipoArma = 1L;
        when(armaRepository.findByTipoArma_IdTipoArma(idTipoArma)).thenReturn(List.of(arma));

        // When
        List<ArmaDTO> resultado = armaService.listarPorTipo(idTipoArma);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Espada", resultado.get(0).getNombreTipo());
        verify(armaRepository, times(1)).findByTipoArma_IdTipoArma(idTipoArma);
    }

    @Test
    void listarPorRareza_debeRetornarListaDeArmas() {
        // Given
        Long idRarezaArma = 1L;
        when(armaRepository.findByRarezaArma_IdRarezaArma(idRarezaArma)).thenReturn(List.of(arma));

        // When
        List<ArmaDTO> resultado = armaService.listarPorRareza(idRarezaArma);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Épica", resultado.get(0).getNombreRareza());
        verify(armaRepository, times(1)).findByRarezaArma_IdRarezaArma(idRarezaArma);
    }

    @Test
    void listarPorNivel_debeRetornarListaDeArmas() {
        // Given
        Integer nivelMinimo = 10;
        when(armaRepository.findByNivelMinimo(nivelMinimo)).thenReturn(List.of(arma));

        // When
        List<ArmaDTO> resultado = armaService.listarPorNivel(nivelMinimo);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(10, resultado.get(0).getNivelMinimo());
        verify(armaRepository, times(1)).findByNivelMinimo(nivelMinimo);
    }

    @Test
    void buscarPorNombre_debeRetornarListaDeArmas() {
        // Given
        String nombreArma = "Espada";
        when(armaRepository.findByNombreArmaContainingIgnoreCase(nombreArma)).thenReturn(List.of(arma));

        // When
        List<ArmaDTO> resultado = armaService.buscarPorNombre(nombreArma);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getNombreArma().contains("Espada"));
        verify(armaRepository, times(1)).findByNombreArmaContainingIgnoreCase(nombreArma);
    }

    @Test
    void buscarPorPrecio_debeRetornarListaDeArmas() {
        // Given
        Integer precio = 500;
        when(armaRepository.findByPrecioLessThanEqual(precio)).thenReturn(List.of(arma));

        // When
        List<ArmaDTO> resultado = armaService.buscarPorPrecio(precio);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getPrecio() <= precio);
        verify(armaRepository, times(1)).findByPrecioLessThanEqual(precio);
    }

    @Test
    void buscarPorId_conIdValido_debeRetornarArma() {
        // Given
        Long idArma = 1L;
        when(armaRepository.findById(idArma)).thenReturn(Optional.of(arma));

        // When
        ArmaDTO resultado = armaService.buscarPorId(idArma);

        // Then
        assertNotNull(resultado);
        assertEquals(idArma, resultado.getIdArma());
        assertEquals("Espada de Fuego", resultado.getNombreArma());
        verify(armaRepository, times(1)).findById(idArma);
    }

    @Test
    void buscarPorId_conIdNoExistente_debeLanzarExcepcion() {
        // Given
        Long idArma = 99L;
        when(armaRepository.findById(idArma)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            armaService.buscarPorId(idArma);
        });

        assertEquals("Arma no encontrada", exception.getMessage());
        verify(armaRepository, times(1)).findById(idArma);
    }
}
