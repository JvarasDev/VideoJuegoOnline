package cl.videojuego.ranking_service.controller;

import cl.videojuego.ranking_service.dto.RankingDTO;
import cl.videojuego.ranking_service.dto.RankingRegistroDTO;
import cl.videojuego.ranking_service.exception.RecursoNoEncontradoException;
import cl.videojuego.ranking_service.service.RankingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RankingController.class)
class RankingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RankingService rankingService;

    private RankingDTO rankingDTO;
    private RankingRegistroDTO registroDTO;

    @BeforeEach
    void setUp() {
        rankingDTO = new RankingDTO();
        rankingDTO.setIdRanking(1L);
        rankingDTO.setIdPersonaje(10L);
        rankingDTO.setNombrePersonaje("Heroe");
        rankingDTO.setPuntos(1500);
        rankingDTO.setVictorias(30);
        rankingDTO.setDerrotas(10);
        rankingDTO.setPosicion(5);
        rankingDTO.setNombreTemporada("Temporada 1");
        rankingDTO.setNombreLiga("Liga Bronce");

        registroDTO = new RankingRegistroDTO();
        registroDTO.setIdPersonaje(10L);
        registroDTO.setIdTemporada(1L);
        registroDTO.setIdLiga(1L);
        registroDTO.setPuntos(1500);
        registroDTO.setVictorias(30);
        registroDTO.setDerrotas(10);
        registroDTO.setPosicion(5);
    }

    @Test
    @DisplayName("Debe retornar 200 OK y la lista de rankings al solicitar todos los registros")
    void shouldReturn200AndRankingList_WhenListarTodosIsCalled() throws Exception {
        // Given
        given(rankingService.listarTodos()).willReturn(List.of(rankingDTO));

        // When / Then
        mockMvc.perform(get("/api/rankings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].idRanking").value(1))
                .andExpect(jsonPath("$[0].nombrePersonaje").value("Heroe"))
                .andExpect(jsonPath("$[0].puntos").value(1500));

        then(rankingService).should().listarTodos();
    }

    @Test
    @DisplayName("Debe retornar 201 Created y el ranking creado al enviar datos válidos")
    void shouldReturn201AndRanking_WhenRegistrarIsCalledWithValidPayload() throws Exception {
        // Given
        given(rankingService.registrar(any(RankingRegistroDTO.class))).willReturn(rankingDTO);

        // When / Then
        mockMvc.perform(post("/api/rankings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registroDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idRanking").value(1))
                .andExpect(jsonPath("$.nombrePersonaje").value("Heroe"))
                .andExpect(jsonPath("$.nombreLiga").value("Liga Bronce"));

        then(rankingService).should().registrar(any(RankingRegistroDTO.class));
    }

    @Test
    @DisplayName("Debe retornar 400 Bad Request si el payload de registro es inválido")
    void shouldReturn400_WhenRegistrarIsCalledWithInvalidPayload() throws Exception {
        // Given
        registroDTO.setIdPersonaje(null); // Provoca un fallo de validación @NotNull

        // When / Then
        mockMvc.perform(post("/api/rankings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registroDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.message").value("Error en la validación de los datos proporcionados"))
                .andExpect(jsonPath("$.detalles.idPersonaje").value("El personaje es obligatorio"));

        then(rankingService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("Debe retornar 200 OK y rankings al buscar por personaje")
    void shouldReturn200AndRankingList_WhenListarPorPersonajeIsCalled() throws Exception {
        // Given
        given(rankingService.listarPorPersonaje(10L)).willReturn(List.of(rankingDTO));

        // When / Then
        mockMvc.perform(get("/api/rankings/personaje/{idPersonaje}", 10L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].idPersonaje").value(10));

        then(rankingService).should().listarPorPersonaje(10L);
    }

    @Test
    @DisplayName("Debe retornar 200 OK y rankings al buscar por liga")
    void shouldReturn200AndRankingList_WhenListarPorLigaIsCalled() throws Exception {
        // Given
        given(rankingService.listarPorLiga(1L)).willReturn(List.of(rankingDTO));

        // When / Then
        mockMvc.perform(get("/api/rankings/liga/{idLiga}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1));

        then(rankingService).should().listarPorLiga(1L);
    }

    @Test
    @DisplayName("Debe retornar 200 OK y rankings al buscar por temporada")
    void shouldReturn200AndRankingList_WhenListarPorTemporadaIsCalled() throws Exception {
        // Given
        given(rankingService.listarPorTemporada(1L)).willReturn(List.of(rankingDTO));

        // When / Then
        mockMvc.perform(get("/api/rankings/temporada/{idTemporada}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1));

        then(rankingService).should().listarPorTemporada(1L);
    }

    @Test
    @DisplayName("Debe retornar 200 OK y rankings al buscar por puntos mínimos")
    void shouldReturn200AndRankingList_WhenListarPorPuntosMinimosIsCalled() throws Exception {
        // Given
        given(rankingService.listarPorPuntosMinimos(1000)).willReturn(List.of(rankingDTO));

        // When / Then
        mockMvc.perform(get("/api/rankings/puntos-minimos")
                        .param("puntos", "1000"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1));

        then(rankingService).should().listarPorPuntosMinimos(1000);
    }

    @Test
    @DisplayName("Debe retornar 200 OK y el top 10 de rankings")
    void shouldReturn200AndRankingList_WhenListarTop10IsCalled() throws Exception {
        // Given
        given(rankingService.listarTop10()).willReturn(List.of(rankingDTO));

        // When / Then
        mockMvc.perform(get("/api/rankings/top10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1));

        then(rankingService).should().listarTop10();
    }

    @Test
    @DisplayName("Debe retornar 200 OK y el ranking al buscar por ID")
    void shouldReturn200AndRanking_WhenBuscarPorIdIsCalled() throws Exception {
        // Given
        given(rankingService.buscarPorId(1L)).willReturn(rankingDTO);

        // When / Then
        mockMvc.perform(get("/api/rankings/{idRanking}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idRanking").value(1))
                .andExpect(jsonPath("$.nombrePersonaje").value("Heroe"));

        then(rankingService).should().buscarPorId(1L);
    }

    @Test
    @DisplayName("Debe retornar 404 Not Found si el ranking no existe")
    void shouldReturn404_WhenBuscarPorIdThrowsRecursoNoEncontradoException() throws Exception {
        // Given
        given(rankingService.buscarPorId(99L))
                .willThrow(new RecursoNoEncontradoException("Ranking no encontrado"));

        // When / Then
        mockMvc.perform(get("/api/rankings/{idRanking}", 99L))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Ranking no encontrado"));

        then(rankingService).should().buscarPorId(99L);
    }
}
