package cl.videojuego.mision_service.controller;

import cl.videojuego.mision_service.dto.MisionDTO;
import cl.videojuego.mision_service.dto.MisionRegistroDTO;
import cl.videojuego.mision_service.service.MisionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MisionController.class)
class MisionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MisionService misionService;

    @Autowired
    private ObjectMapper objectMapper;

    private MisionDTO misionDTOMock;
    private MisionRegistroDTO registroDTOMock;

    @BeforeEach
    void setUp() {
        misionDTOMock = new MisionDTO();
        misionDTOMock.setIdMision(100L);
        misionDTOMock.setNombreMision("Rescatar al príncipe");
        misionDTOMock.setDescripcion("Misión principal");
        misionDTOMock.setRecompensaExperiencia(500);
        misionDTOMock.setRecompensaMonedas(100);
        misionDTOMock.setNivelMinimo(5);
        misionDTOMock.setNombreTipo("Historia");
        misionDTOMock.setNombreEstado("Activa");

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
    @DisplayName("GET /api/misiones - Debería retornar 200 OK y la lista de misiones")
    void deberiaListarTodasLasMisiones() throws Exception {
        // Given
        when(misionService.listarTodas()).thenReturn(Arrays.asList(misionDTOMock));

        // When & Then
        mockMvc.perform(get("/api/misiones")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nombreMision").value("Rescatar al príncipe"))
                .andExpect(jsonPath("$[0].nombreTipo").value("Historia"));
    }

    @Test
    @DisplayName("GET /api/misiones - Debería retornar 200 OK y lista vacía si no hay misiones")
    void deberiaRetornarListaVaciaAlListarTodas() throws Exception {
        // Given
        when(misionService.listarTodas()).thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(get("/api/misiones")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("POST /api/misiones - Debería retornar 201 Created al registrar misión válida")
    void deberiaRegistrarMision() throws Exception {
        // Given
        when(misionService.registrar(any(MisionRegistroDTO.class))).thenReturn(misionDTOMock);

        // When & Then
        mockMvc.perform(post("/api/misiones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registroDTOMock)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idMision").value(100L))
                .andExpect(jsonPath("$.nombreMision").value("Rescatar al príncipe"))
                .andExpect(jsonPath("$.nombreEstado").value("Activa"));
    }

    @Test
    @DisplayName("POST /api/misiones - Debería retornar 400 Bad Request si los datos son inválidos")
    void deberiaRetornarBadRequestAlRegistrarConDatosInvalidos() throws Exception {
        // Given
        MisionRegistroDTO dtoInvalido = new MisionRegistroDTO();
        // Faltan campos obligatorios como el nombre y los IDs

        // When & Then
        mockMvc.perform(post("/api/misiones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/misiones/tipo/{id} - Debería retornar 200 OK y misiones del tipo especificado")
    void deberiaListarPorTipo() throws Exception {
        // Given
        Long idTipo = 1L;
        when(misionService.listarPorTipo(idTipo)).thenReturn(Arrays.asList(misionDTOMock));

        // When & Then
        mockMvc.perform(get("/api/misiones/tipo/{idTipoMision}", idTipo)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreTipo").value("Historia"));
    }

    @Test
    @DisplayName("GET /api/misiones/estado/{id} - Debería retornar 200 OK y misiones del estado especificado")
    void deberiaListarPorEstado() throws Exception {
        // Given
        Long idEstado = 1L;
        when(misionService.listarPorEstado(idEstado)).thenReturn(Arrays.asList(misionDTOMock));

        // When & Then
        mockMvc.perform(get("/api/misiones/estado/{idEstadoMision}", idEstado)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreEstado").value("Activa"));
    }

    @Test
    @DisplayName("GET /api/misiones/nivel/{nivel} - Debería retornar 200 OK y misiones filtradas por nivel")
    void deberiaListarPorNivel() throws Exception {
        // Given
        Integer nivelMinimo = 5;
        when(misionService.listarPorNivel(nivelMinimo)).thenReturn(Arrays.asList(misionDTOMock));

        // When & Then
        mockMvc.perform(get("/api/misiones/nivel/{nivelMinimo}", nivelMinimo)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nivelMinimo").value(5));
    }

    @Test
    @DisplayName("GET /api/misiones/buscar-por-nombre - Debería retornar 200 OK y misiones que contengan el nombre")
    void deberiaBuscarPorNombre() throws Exception {
        // Given
        String nombre = "príncipe";
        when(misionService.buscarPorNombre(nombre)).thenReturn(Arrays.asList(misionDTOMock));

        // When & Then
        mockMvc.perform(get("/api/misiones/buscar-por-nombre")
                .param("nombre", nombre)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreMision").value("Rescatar al príncipe"));
    }

    @Test
    @DisplayName("GET /api/misiones/recompensa-menor - Debería retornar 200 OK y misiones filtradas por recompensa")
    void deberiaBuscarPorRecompensa() throws Exception {
        // Given
        Integer recompensa = 150;
        when(misionService.buscarPorRecompensa(recompensa)).thenReturn(Arrays.asList(misionDTOMock));

        // When & Then
        mockMvc.perform(get("/api/misiones/recompensa-menor")
                .param("recompensa", recompensa.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recompensaMonedas").value(100));
    }
}
