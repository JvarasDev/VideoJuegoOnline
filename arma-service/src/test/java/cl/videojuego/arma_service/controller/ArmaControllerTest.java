package cl.videojuego.arma_service.controller;

import cl.videojuego.arma_service.dto.ArmaDTO;
import cl.videojuego.arma_service.dto.ArmaRegistroDTO;
import cl.videojuego.arma_service.exception.RecursoNoEncontradoException;
import cl.videojuego.arma_service.service.ArmaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(ArmaController.class)
class ArmaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArmaService armaService;

    @Autowired
    private ObjectMapper objectMapper;

    private ArmaDTO armaDTO;
    private ArmaRegistroDTO armaRegistroDTO;

    @BeforeEach
    void setUp() {
        armaDTO = new ArmaDTO();
        armaDTO.setIdArma(1L);
        armaDTO.setNombreArma("Espada de Fuego");
        armaDTO.setDanio(100);
        armaDTO.setNivelMinimo(10);
        armaDTO.setPrecio(500);
        armaDTO.setNombreTipo("Espada");
        armaDTO.setNombreRareza("Épica");
        armaDTO.setMultiplicadorDanio(1.5);

        armaRegistroDTO = new ArmaRegistroDTO();
        armaRegistroDTO.setNombreArma("Espada de Fuego");
        armaRegistroDTO.setDanio(100);
        armaRegistroDTO.setNivelMinimo(10);
        armaRegistroDTO.setPrecio(500);
        armaRegistroDTO.setIdTipoArma(1L);
        armaRegistroDTO.setIdRarezaArma(1L);
    }

    @Test
    void listarTodas_debeRetornarEstado200YListaDeArmas() throws Exception {
        // Given
        when(armaService.listarTodas()).thenReturn(List.of(armaDTO));

        // When & Then
        mockMvc.perform(get("/api/armas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idArma", is(1)))
                .andExpect(jsonPath("$[0].nombreArma", is("Espada de Fuego")));

        verify(armaService, times(1)).listarTodas();
    }

    @Test
    void registrar_conDatosValidos_debeRetornarEstado201YArmaCreada() throws Exception {
        // Given
        when(armaService.registrar(any(ArmaRegistroDTO.class))).thenReturn(armaDTO);

        // When & Then
        mockMvc.perform(post("/api/armas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(armaRegistroDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idArma", is(1)))
                .andExpect(jsonPath("$.nombreArma", is("Espada de Fuego")))
                .andExpect(jsonPath("$.nombreTipo", is("Espada")));

        verify(armaService, times(1)).registrar(any(ArmaRegistroDTO.class));
    }

    @Test
    void registrar_conDatosInvalidos_debeRetornarEstado400() throws Exception {
        // Given
        ArmaRegistroDTO dtoInvalido = new ArmaRegistroDTO();
        // Faltan campos obligatorios para forzar el error de validación

        // When & Then
        mockMvc.perform(post("/api/armas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("El nombre del arma es obligatorio"))); // Según el
                                                                                                        // GlobalExceptionHandler
                                                                                                        // retorna una
                                                                                                        // concatenación

        verify(armaService, never()).registrar(any(ArmaRegistroDTO.class));
    }

    @Test
    void listarPorTipo_debeRetornarEstado200YListaDeArmas() throws Exception {
        // Given
        Long idTipoArma = 1L;
        when(armaService.listarPorTipo(idTipoArma)).thenReturn(List.of(armaDTO));

        // When & Then
        mockMvc.perform(get("/api/armas/tipo/{idTipoArma}", idTipoArma)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombreTipo", is("Espada")));

        verify(armaService, times(1)).listarPorTipo(idTipoArma);
    }

    @Test
    void listarPorRareza_debeRetornarEstado200YListaDeArmas() throws Exception {
        // Given
        Long idRarezaArma = 1L;
        when(armaService.listarPorRareza(idRarezaArma)).thenReturn(List.of(armaDTO));

        // When & Then
        mockMvc.perform(get("/api/armas/rareza/{idRarezaArma}", idRarezaArma)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombreRareza", is("Épica")));

        verify(armaService, times(1)).listarPorRareza(idRarezaArma);
    }

    @Test
    void listarPorNivel_debeRetornarEstado200YListaDeArmas() throws Exception {
        // Given
        Integer nivelMinimo = 10;
        when(armaService.listarPorNivel(nivelMinimo)).thenReturn(List.of(armaDTO));

        // When & Then
        mockMvc.perform(get("/api/armas/nivel/{nivelMinimo}", nivelMinimo)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nivelMinimo", is(10)));

        verify(armaService, times(1)).listarPorNivel(nivelMinimo);
    }

    @Test
    void buscarPorNombre_debeRetornarEstado200YListaDeArmas() throws Exception {
        // Given
        String nombre = "Espada";
        when(armaService.buscarPorNombre(nombre)).thenReturn(List.of(armaDTO));

        // When & Then
        mockMvc.perform(get("/api/armas/buscar-por-nombre")
                .param("nombre", nombre)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombreArma", containsString("Espada")));

        verify(armaService, times(1)).buscarPorNombre(nombre);
    }

    @Test
    void buscarPorPrecio_debeRetornarEstado200YListaDeArmas() throws Exception {
        // Given
        Integer precio = 500;
        when(armaService.buscarPorPrecio(precio)).thenReturn(List.of(armaDTO));

        // When & Then
        mockMvc.perform(get("/api/armas/precio-menor")
                .param("precio", String.valueOf(precio))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].precio", is(500)));

        verify(armaService, times(1)).buscarPorPrecio(precio);
    }

    @Test
    void buscarPorId_conIdValido_debeRetornarEstado200YArma() throws Exception {
        // Given
        Long idArma = 1L;
        when(armaService.buscarPorId(idArma)).thenReturn(armaDTO);

        // When & Then
        mockMvc.perform(get("/api/armas/{idArma}", idArma)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idArma", is(1)))
                .andExpect(jsonPath("$.nombreArma", is("Espada de Fuego")));

        verify(armaService, times(1)).buscarPorId(idArma);
    }

    @Test
    void buscarPorId_conIdNoExistente_debeRetornarEstado404() throws Exception {
        // Given
        Long idArma = 99L;
        when(armaService.buscarPorId(idArma))
                .thenThrow(new RecursoNoEncontradoException("Arma no encontrada"));

        // When & Then
        mockMvc.perform(get("/api/armas/{idArma}", idArma)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Arma no encontrada")));

        verify(armaService, times(1)).buscarPorId(idArma);
    }
}
