package cl.videojuego.personaje_service.controller;

import cl.videojuego.personaje_service.dto.PersonajeDTO;
import cl.videojuego.personaje_service.dto.PersonajeRegistroDTO;
import cl.videojuego.personaje_service.exception.RecursoNoEncontradoException;
import cl.videojuego.personaje_service.exception.UsuarioNoAptoException;
import cl.videojuego.personaje_service.model.ClasePersonaje;
import cl.videojuego.personaje_service.model.EstadoPersonaje;
import cl.videojuego.personaje_service.service.ClasePersonajeService;
import cl.videojuego.personaje_service.service.EstadoPersonajeService;
import cl.videojuego.personaje_service.service.PersonajeService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonajeController.class)
class PersonajeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonajeService personajeService;

    @MockBean
    private ClasePersonajeService clasePersonajeService;

    @MockBean
    private EstadoPersonajeService estadoPersonajeService;

    private PersonajeDTO personajeDTO;
    private PersonajeRegistroDTO registroDTO;
    private ClasePersonaje clasePersonaje;
    private EstadoPersonaje estadoPersonaje;

    @BeforeEach
    void setUp() {
        personajeDTO = new PersonajeDTO();
        personajeDTO.setIdPersonaje(1L);
        personajeDTO.setNombre("Thor");
        personajeDTO.setNivel(10);
        personajeDTO.setIdUsuario(1L);

        registroDTO = new PersonajeRegistroDTO();
        registroDTO.setIdUsuario(1L);
        registroDTO.setIdClasePersonaje(1L);
        registroDTO.setIdEstadoPersonaje(1L);
        registroDTO.setNombre("Thor");

        clasePersonaje = new ClasePersonaje();
        clasePersonaje.setIdClasePersonaje(1L);
        clasePersonaje.setNombreClase("Guerrero");

        estadoPersonaje = new EstadoPersonaje();
        estadoPersonaje.setIdEstadoPersonaje(1L);
        estadoPersonaje.setNombreEstado("Activo");
    }

    @Test
    @DisplayName("Debe retornar 200 OK y la lista de personajes al llamar a listarTodos")
    void shouldReturn200AndPersonajesListWhenListarTodosIsCalled() throws Exception {
        // Given
        given(personajeService.listarTodos()).willReturn(List.of(personajeDTO));

        // When & Then
        mockMvc.perform(get("/api/personajes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].idPersonaje").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Thor"))
                .andExpect(jsonPath("$[0].nivel").value(10));
    }

    @Test
    @DisplayName("Debe retornar 201 Created al registrar un personaje exitosamente")
    void shouldReturn201AndPersonajeWhenRegistrarIsCalled() throws Exception {
        // Given
        given(personajeService.registrar(any(PersonajeRegistroDTO.class))).willReturn(personajeDTO);

        // When & Then
        mockMvc.perform(post("/api/personajes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registroDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idPersonaje").value(1L))
                .andExpect(jsonPath("$.nombre").value("Thor"));
    }

    @Test
    @DisplayName("Debe retornar 400 Bad Request si el payload de registro es invalido")
    void shouldReturn400WhenRegistrarPayloadIsInvalid() throws Exception {
        // Given
        PersonajeRegistroDTO invalidDto = new PersonajeRegistroDTO(); // Sin campos obligatorios para disparar validacion

        // When & Then
        mockMvc.perform(post("/api/personajes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Error"));
    }

    @Test
    @DisplayName("Debe retornar 403 Forbidden si el usuario no es apto al registrar")
    void shouldReturn403WhenUsuarioNoAptoExceptionIsThrown() throws Exception {
        // Given
        given(personajeService.registrar(any(PersonajeRegistroDTO.class)))
                .willThrow(new UsuarioNoAptoException("Usuario baneado"));

        // When & Then
        mockMvc.perform(post("/api/personajes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registroDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").value(403))
                .andExpect(jsonPath("$.message").value("Usuario baneado"));
    }

    @Test
    @DisplayName("Debe retornar 200 OK y el personaje al buscar por id exitosamente")
    void shouldReturn200AndPersonajeWhenBuscarPorIdIsCalled() throws Exception {
        // Given
        given(personajeService.buscarPorId(1L)).willReturn(personajeDTO);

        // When & Then
        mockMvc.perform(get("/api/personajes/{idPersonaje}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idPersonaje").value(1L))
                .andExpect(jsonPath("$.nombre").value("Thor"));
    }

    @Test
    @DisplayName("Debe retornar 404 Not Found cuando el personaje no existe")
    void shouldReturn404WhenPersonajeNotFound() throws Exception {
        // Given
        given(personajeService.buscarPorId(99L))
                .willThrow(new RecursoNoEncontradoException("Personaje no encontrado"));

        // When & Then
        mockMvc.perform(get("/api/personajes/{idPersonaje}", 99L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Personaje no encontrado"));
    }

    @Test
    @DisplayName("Debe retornar 200 OK y la lista de clases")
    void shouldReturn200AndClasesWhenListarClasesIsCalled() throws Exception {
        // Given
        given(clasePersonajeService.listarClases()).willReturn(List.of(clasePersonaje));

        // When & Then
        mockMvc.perform(get("/api/personajes/clases")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nombreClase").value("Guerrero"));
    }

    @Test
    @DisplayName("Debe retornar 200 OK y la lista de estados")
    void shouldReturn200AndEstadosWhenListarEstadosIsCalled() throws Exception {
        // Given
        given(estadoPersonajeService.listarEstados()).willReturn(List.of(estadoPersonaje));

        // When & Then
        mockMvc.perform(get("/api/personajes/estados")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nombreEstado").value("Activo"));
    }

    @Test
    @DisplayName("Debe retornar 200 OK al listar personajes por id de usuario")
    void shouldReturn200WhenListarPorUsuarioIsCalled() throws Exception {
        // Given
        given(personajeService.listarPorUsuario(1L)).willReturn(List.of(personajeDTO));

        // When & Then
        mockMvc.perform(get("/api/personajes/usuario/{idUsuario}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }
}
