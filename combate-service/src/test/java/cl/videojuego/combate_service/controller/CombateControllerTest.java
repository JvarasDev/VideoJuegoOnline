package cl.videojuego.combate_service.controller;

import cl.videojuego.combate_service.dto.CombateDTO;
import cl.videojuego.combate_service.dto.CombateRegistroDTO;
import cl.videojuego.combate_service.exception.GlobalExceptionHandler;
import cl.videojuego.combate_service.exception.ResultadoCombateInvalidoException;
import cl.videojuego.combate_service.service.CombateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CombateController.class)
class CombateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CombateService combateService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/combates - Debe retornar lista de combates y status 200 OK")
    void listarTodos_DeberiaRetornarListaYStatus200() throws Exception {
        // Given
        CombateDTO combate1 = new CombateDTO();
        combate1.setIdCombate(1L);
        CombateDTO combate2 = new CombateDTO();
        combate2.setIdCombate(2L);
        List<CombateDTO> combates = Arrays.asList(combate1, combate2);

        when(combateService.listarTodos()).thenReturn(combates);

        // When & Then
        mockMvc.perform(get("/api/combates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].idCombate", is(1)))
                .andExpect(jsonPath("$[1].idCombate", is(2)));

        verify(combateService, times(1)).listarTodos();
    }

    @Test
    @DisplayName("POST /api/combates - Con datos válidos, debe registrar combate y retornar 201 Created")
    void registrar_ConDatosValidos_DeberiaRetornarCombateYStatus201() throws Exception {
        // Given
        CombateRegistroDTO registroDTO = new CombateRegistroDTO();
        registroDTO.setIdPersonajeAtacante(1L);
        registroDTO.setIdPersonajeDefensor(2L);
        registroDTO.setIdGanador(1L);
        registroDTO.setIdTipoCombate(1L);
        registroDTO.setIdEstadoCombate(1L);
        registroDTO.setExperienciaGanada(100);
        registroDTO.setMonedasGanadas(50);
        registroDTO.setDuracionSegundos(120);

        CombateDTO combateEsperado = new CombateDTO();
        combateEsperado.setIdCombate(10L);

        when(combateService.registrar(any(CombateRegistroDTO.class))).thenReturn(combateEsperado);

        // When & Then
        mockMvc.perform(post("/api/combates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registroDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idCombate", is(10)));

        verify(combateService, times(1)).registrar(any(CombateRegistroDTO.class));
    }

    @Test
    @DisplayName("POST /api/combates - Con datos faltantes o inválidos, debe retornar 400 Bad Request")
    void registrar_ConDatosInvalidos_DeberiaRetornarStatus400() throws Exception {
        // Given
        CombateRegistroDTO registroDTO = new CombateRegistroDTO();
        // Faltan todos los campos obligatorios para provocar un error de validación por la anotación @Valid

        // When & Then
        mockMvc.perform(post("/api/combates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registroDTO)))
                .andExpect(status().isBadRequest());

        verify(combateService, never()).registrar(any(CombateRegistroDTO.class));
    }

    @Test
    @DisplayName("POST /api/combates - Cuando el ganador es inválido, debe retornar 400 Bad Request")
    void registrar_ConGanadorInvalido_DeberiaRetornarStatus400() throws Exception {
        // Given
        CombateRegistroDTO registroDTO = new CombateRegistroDTO();
        registroDTO.setIdPersonajeAtacante(1L);
        registroDTO.setIdPersonajeDefensor(2L);
        registroDTO.setIdGanador(3L);
        registroDTO.setIdTipoCombate(1L);
        registroDTO.setIdEstadoCombate(1L);
        registroDTO.setExperienciaGanada(100);
        registroDTO.setMonedasGanadas(50);
        registroDTO.setDuracionSegundos(120);

        when(combateService.registrar(any(CombateRegistroDTO.class)))
                .thenThrow(new ResultadoCombateInvalidoException("El ganador debe ser el atacante o el defensor"));

        // When & Then
        mockMvc.perform(post("/api/combates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registroDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("El ganador debe ser el atacante o el defensor"));

        verify(combateService, times(1)).registrar(any(CombateRegistroDTO.class));
    }

    @Test
    @DisplayName("GET /api/combates/atacante/{idPersonaje} - Debe retornar combates por atacante")
    void listarPorAtacante_DeberiaRetornarListaYStatus200() throws Exception {
        // Given
        Long idAtacante = 1L;
        CombateDTO combate1 = new CombateDTO();
        combate1.setIdCombate(1L);
        
        when(combateService.listarPorAtacante(idAtacante)).thenReturn(Collections.singletonList(combate1));

        // When & Then
        mockMvc.perform(get("/api/combates/atacante/{idPersonaje}", idAtacante))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idCombate", is(1)));

        verify(combateService, times(1)).listarPorAtacante(idAtacante);
    }

    @Test
    @DisplayName("GET /api/combates/defensor/{idPersonaje} - Debe retornar combates por defensor")
    void listarPorDefensor_DeberiaRetornarListaYStatus200() throws Exception {
        // Given
        Long idDefensor = 2L;
        CombateDTO combate1 = new CombateDTO();
        combate1.setIdCombate(1L);
        
        when(combateService.listarPorDefensor(idDefensor)).thenReturn(Collections.singletonList(combate1));

        // When & Then
        mockMvc.perform(get("/api/combates/defensor/{idPersonaje}", idDefensor))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idCombate", is(1)));

        verify(combateService, times(1)).listarPorDefensor(idDefensor);
    }

    @Test
    @DisplayName("GET /api/combates/ganador/{idGanador} - Debe retornar combates por ganador")
    void listarPorGanador_DeberiaRetornarListaYStatus200() throws Exception {
        // Given
        Long idGanador = 1L;
        CombateDTO combate1 = new CombateDTO();
        combate1.setIdCombate(1L);
        
        when(combateService.listarPorGanador(idGanador)).thenReturn(Collections.singletonList(combate1));

        // When & Then
        mockMvc.perform(get("/api/combates/ganador/{idGanador}", idGanador))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idCombate", is(1)));

        verify(combateService, times(1)).listarPorGanador(idGanador);
    }

    @Test
    @DisplayName("GET /api/combates/tipo/{idTipoCombate} - Debe retornar combates por tipo")
    void listarPorTipo_DeberiaRetornarListaYStatus200() throws Exception {
        // Given
        Long idTipo = 1L;
        CombateDTO combate1 = new CombateDTO();
        combate1.setIdCombate(1L);
        
        when(combateService.listarPorTipo(idTipo)).thenReturn(Collections.singletonList(combate1));

        // When & Then
        mockMvc.perform(get("/api/combates/tipo/{idTipoCombate}", idTipo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idCombate", is(1)));

        verify(combateService, times(1)).listarPorTipo(idTipo);
    }

    @Test
    @DisplayName("GET /api/combates/estado/{idEstadoCombate} - Debe retornar combates por estado")
    void listarPorEstado_DeberiaRetornarListaYStatus200() throws Exception {
        // Given
        Long idEstado = 1L;
        CombateDTO combate1 = new CombateDTO();
        combate1.setIdCombate(1L);
        
        when(combateService.listarPorEstado(idEstado)).thenReturn(Collections.singletonList(combate1));

        // When & Then
        mockMvc.perform(get("/api/combates/estado/{idEstadoCombate}", idEstado))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idCombate", is(1)));

        verify(combateService, times(1)).listarPorEstado(idEstado);
    }
}
