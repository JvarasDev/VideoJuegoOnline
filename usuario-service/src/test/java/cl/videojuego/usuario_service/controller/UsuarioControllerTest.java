package cl.videojuego.usuario_service.controller;

import cl.videojuego.usuario_service.dto.UsuarioDTO;
import cl.videojuego.usuario_service.dto.UsuarioRegistroDTO;
import cl.videojuego.usuario_service.exception.RecursoNoEncontradoException;
import cl.videojuego.usuario_service.service.EstadoUsuarioService;
import cl.videojuego.usuario_service.service.RolService;
import cl.videojuego.usuario_service.service.UsuarioService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private RolService rolService;

    @MockBean
    private EstadoUsuarioService estadoUsuarioService;

    private UsuarioDTO usuarioDTO;
    private UsuarioRegistroDTO usuarioRegistroDTO;

    @BeforeEach
    void setUp() {
        usuarioDTO = new UsuarioDTO(
                1L, "Juan", "Perez", "juan@test.com", 1, "JUGADOR", "ACTIVO"
        );

        usuarioRegistroDTO = new UsuarioRegistroDTO(
                "Juan", "Perez", "juan@test.com", "secreta123", 1L, 1L
        );
    }

    @Test
    void shouldReturn200AndUserList_WhenListarUsuariosIsCalled() throws Exception {
        // Given
        given(usuarioService.listarUsuarios()).willReturn(List.of(usuarioDTO));

        // When / Then
        mockMvc.perform(get("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].idUsuario").value(1))
                .andExpect(jsonPath("$.data[0].correo").value("juan@test.com"))
                .andExpect(jsonPath("$.message").value("Usuarios listados exitosamente"));
    }

    @Test
    void shouldReturn200AndEmptyList_WhenListarUsuariosFindsNoData() throws Exception {
        // Given
        given(usuarioService.listarUsuarios()).willReturn(Collections.emptyList());

        // When / Then
        mockMvc.perform(get("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.message").value("Usuarios listados exitosamente"));
    }

    @Test
    void shouldReturn200AndUser_WhenBuscarUsuarioPorIdIsSuccessful() throws Exception {
        // Given
        given(usuarioService.buscarPorId(1L)).willReturn(usuarioDTO);

        // When / Then
        mockMvc.perform(get("/api/usuarios/{idUsuario}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.idUsuario").value(1))
                .andExpect(jsonPath("$.data.correo").value("juan@test.com"))
                .andExpect(jsonPath("$.message").value("Usuario encontrado exitosamente"));
    }

    @Test
    void shouldReturn404_WhenBuscarUsuarioPorIdFindsNoUser() throws Exception {
        // Given
        given(usuarioService.buscarPorId(99L))
                .willThrow(new RecursoNoEncontradoException("Usuario no encontrado con ID: 99"));

        // When / Then
        mockMvc.perform(get("/api/usuarios/{idUsuario}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Usuario no encontrado con ID: 99"));
    }

    @Test
    void shouldReturn201AndUser_WhenRegistrarUsuarioIsSuccessful() throws Exception {
        // Given
        given(usuarioService.registrarUsuario(any(UsuarioRegistroDTO.class))).willReturn(usuarioDTO);

        // When / Then
        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRegistroDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.idUsuario").value(1))
                .andExpect(jsonPath("$.data.correo").value("juan@test.com"))
                .andExpect(jsonPath("$.message").value("Usuario registrado exitosamente"));
    }

    @Test
    void shouldReturn400_WhenRegistrarUsuarioHasInvalidData() throws Exception {
        // Given
        UsuarioRegistroDTO invalidDTO = new UsuarioRegistroDTO(
                "", "", "no-es-correo", "123", null, null
        );

        // When / Then
        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturn200AndUser_WhenActualizarUsuarioIsSuccessful() throws Exception {
        // Given
        given(usuarioService.actualizarUsuario(eq(1L), any(UsuarioRegistroDTO.class))).willReturn(usuarioDTO);

        // When / Then
        mockMvc.perform(put("/api/usuarios/{idUsuario}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRegistroDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.idUsuario").value(1))
                .andExpect(jsonPath("$.data.correo").value("juan@test.com"))
                .andExpect(jsonPath("$.message").value("Usuario actualizado exitosamente"));
    }

    @Test
    void shouldReturn404_WhenActualizarUsuarioFindsNoUser() throws Exception {
        // Given
        given(usuarioService.actualizarUsuario(eq(99L), any(UsuarioRegistroDTO.class)))
                .willThrow(new RecursoNoEncontradoException("Usuario no encontrado con ID: 99"));

        // When / Then
        mockMvc.perform(put("/api/usuarios/{idUsuario}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRegistroDTO)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Usuario no encontrado con ID: 99"));
    }

    @Test
    void shouldReturn200_WhenEliminarUsuarioIsSuccessful() throws Exception {
        // Given - void method so nothing to mock by default
        
        // When / Then
        mockMvc.perform(delete("/api/usuarios/{idUsuario}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Usuario eliminado exitosamente"));
    }

    @Test
    void shouldReturn404_WhenEliminarUsuarioFindsNoUser() throws Exception {
        // Given
        doThrow(new RecursoNoEncontradoException("Usuario no encontrado con ID: 99"))
                .when(usuarioService).eliminarUsuario(99L);

        // When / Then
        mockMvc.perform(delete("/api/usuarios/{idUsuario}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Usuario no encontrado con ID: 99"));
    }
}
