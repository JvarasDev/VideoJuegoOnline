package cl.videojuego.inventario_service.controller;

import cl.videojuego.inventario_service.dto.InventarioDTO;
import cl.videojuego.inventario_service.dto.InventarioRegistroDTO;
import cl.videojuego.inventario_service.exception.RecursoNoEncontradoException;
import cl.videojuego.inventario_service.service.InventarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventarioController.class)
class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventarioService inventarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listarTodos_DebeRetornar200YListaDeInventarios() throws Exception {
        // Given
        InventarioDTO inventarioDTO = new InventarioDTO();
        inventarioDTO.setIdInventario(1L);
        inventarioDTO.setIdPersonaje(10L);
        inventarioDTO.setCapacidadMaxima(50);
        inventarioDTO.setNombreEstado("ACTIVO");

        when(inventarioService.listarTodos()).thenReturn(List.of(inventarioDTO));

        // When & Then
        mockMvc.perform(get("/api/inventarios")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idInventario").value(1))
                .andExpect(jsonPath("$[0].idPersonaje").value(10))
                .andExpect(jsonPath("$[0].capacidadMaxima").value(50))
                .andExpect(jsonPath("$[0].nombreEstado").value("ACTIVO"));

        verify(inventarioService, times(1)).listarTodos();
    }

    @Test
    void registrar_ConDatosValidos_DebeRetornar201YInventarioCreado() throws Exception {
        // Given
        InventarioRegistroDTO registroDTO = new InventarioRegistroDTO();
        registroDTO.setIdPersonaje(10L);
        registroDTO.setIdEstadoInventario(1L);
        registroDTO.setCapacidadMaxima(50);

        InventarioDTO inventarioCreado = new InventarioDTO();
        inventarioCreado.setIdInventario(1L);
        inventarioCreado.setIdPersonaje(10L);
        inventarioCreado.setCapacidadMaxima(50);

        when(inventarioService.registrar(any(InventarioRegistroDTO.class))).thenReturn(inventarioCreado);

        // When & Then
        mockMvc.perform(post("/api/inventarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registroDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idInventario").value(1))
                .andExpect(jsonPath("$.idPersonaje").value(10));

        verify(inventarioService, times(1)).registrar(any(InventarioRegistroDTO.class));
    }

    @Test
    void registrar_SinDatos_DebeRetornar400BadRequest() throws Exception {
        // Given
        InventarioRegistroDTO registroInvalido = new InventarioRegistroDTO(); // Campos nulos, falla validación

        // When & Then
        mockMvc.perform(post("/api/inventarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registroInvalido)))
                .andExpect(status().isBadRequest());
                
        verify(inventarioService, never()).registrar(any(InventarioRegistroDTO.class));
    }

    @Test
    void listarPorPersonaje_DebeRetornar200YListaDeInventarios() throws Exception {
        // Given
        Long idPersonaje = 10L;
        InventarioDTO inventarioDTO = new InventarioDTO();
        inventarioDTO.setIdPersonaje(idPersonaje);

        when(inventarioService.listarPorPersonaje(idPersonaje)).thenReturn(List.of(inventarioDTO));

        // When & Then
        mockMvc.perform(get("/api/inventarios/personaje/{idPersonaje}", idPersonaje)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPersonaje").value(10));

        verify(inventarioService, times(1)).listarPorPersonaje(idPersonaje);
    }

    @Test
    void listarPorEstado_DebeRetornar200YListaDeInventarios() throws Exception {
        // Given
        Long idEstado = 1L;
        InventarioDTO inventarioDTO = new InventarioDTO();
        inventarioDTO.setNombreEstado("ACTIVO");

        when(inventarioService.listarPorEstado(idEstado)).thenReturn(List.of(inventarioDTO));

        // When & Then
        mockMvc.perform(get("/api/inventarios/estado/{idEstadoInventario}", idEstado)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreEstado").value("ACTIVO"));

        verify(inventarioService, times(1)).listarPorEstado(idEstado);
    }

    @Test
    void buscarPorId_CuandoExiste_DebeRetornar200YInventarioDTO() throws Exception {
        // Given
        Long idInventario = 1L;
        InventarioDTO inventarioDTO = new InventarioDTO();
        inventarioDTO.setIdInventario(idInventario);
        inventarioDTO.setIdPersonaje(10L);

        when(inventarioService.buscarPorId(idInventario)).thenReturn(inventarioDTO);

        // When & Then
        mockMvc.perform(get("/api/inventarios/{idInventario}", idInventario)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idInventario").value(1))
                .andExpect(jsonPath("$.idPersonaje").value(10));

        verify(inventarioService, times(1)).buscarPorId(idInventario);
    }

    @Test
    void buscarPorId_CuandoNoExiste_DebeRetornar404NotFound() throws Exception {
        // Given
        Long idInventario = 99L;

        when(inventarioService.buscarPorId(idInventario))
                .thenThrow(new RecursoNoEncontradoException("Inventario no encontrado"));

        // When & Then
        mockMvc.perform(get("/api/inventarios/{idInventario}", idInventario)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(inventarioService, times(1)).buscarPorId(idInventario);
    }
}
