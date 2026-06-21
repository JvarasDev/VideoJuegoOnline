package cl.videojuego.usuario_service.controller;

import cl.videojuego.usuario_service.dto.UsuarioDTO;
import cl.videojuego.usuario_service.dto.UsuarioRegistroDTO;
import cl.videojuego.usuario_service.dto.ApiResponse;
import cl.videojuego.usuario_service.model.EstadoUsuario;
import cl.videojuego.usuario_service.model.Rol;
import cl.videojuego.usuario_service.service.EstadoUsuarioService;
import cl.videojuego.usuario_service.service.RolService;
import cl.videojuego.usuario_service.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador REST para la gestión de usuarios, roles y estados.
 * Base URL: /api/usuarios
 */
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RolService rolService;
    private final EstadoUsuarioService estadoUsuarioService;

    // ─── Usuarios ─────────────────────────────────────────────────────────────
    @Operation(summary = "Listar todos los pagos", description = "Retorna una lista con todos los pagos registrados")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "")
    @GetMapping
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> listarUsuarios() {
        return ResponseEntity
                .ok(ApiResponse.success(usuarioService.listarUsuarios(), "Usuarios listados exitosamente"));
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<ApiResponse<UsuarioDTO>> buscarPorId(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(ApiResponse.success(usuarioService.buscarPorId(idUsuario), "Usuario encontrado"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UsuarioDTO>> registrarUsuario(@Valid @RequestBody UsuarioRegistroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(usuarioService.registrarUsuario(dto), "Usuario registrado exitosamente"));
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<ApiResponse<UsuarioDTO>> actualizarUsuario(
            @PathVariable Long idUsuario,
            @Valid @RequestBody UsuarioRegistroDTO dto) {
        return ResponseEntity.ok(ApiResponse.success(usuarioService.actualizarUsuario(idUsuario, dto),
                "Usuario actualizado exitosamente"));
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<ApiResponse<Void>> eliminarUsuario(@PathVariable Long idUsuario) {
        usuarioService.eliminarUsuario(idUsuario);
        return ResponseEntity.ok(ApiResponse.success(null, "Usuario eliminado exitosamente"));
    }

    @PutMapping("/{idUsuario}/rol-estado")
    public ResponseEntity<ApiResponse<UsuarioDTO>> actualizarRolYEstado(
            @PathVariable Long idUsuario,
            @RequestParam Long idRol,
            @RequestParam Long idEstadoUsuario) {
        return ResponseEntity.ok(ApiResponse.success(
                usuarioService.actualizarRolYEstado(idUsuario, idRol, idEstadoUsuario), "Rol y estado actualizados"));
    }

    // ─── Búsquedas de usuarios ────────────────────────────────────────────────

    @GetMapping("/buscar-por-correo")
    public ResponseEntity<ApiResponse<UsuarioDTO>> buscarPorCorreo(@RequestParam String correo) {
        return ResponseEntity.ok(ApiResponse.success(usuarioService.buscarPorCorreo(correo), "Usuario encontrado"));
    }

    @GetMapping("/rol/{idRol}")
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> listarPorRol(@PathVariable Long idRol) {
        return ResponseEntity
                .ok(ApiResponse.success(usuarioService.listarPorRol(idRol), "Usuarios por rol encontrados"));
    }

    @GetMapping("/estado/{idEstadoUsuario}")
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> listarPorEstado(@PathVariable Long idEstadoUsuario) {
        return ResponseEntity.ok(ApiResponse.success(usuarioService.listarPorEstado(idEstadoUsuario),
                "Usuarios por estado encontrados"));
    }

    @GetMapping("/nivel/{nivelCuenta}")
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> listarPorNivel(@PathVariable Integer nivelCuenta) {
        return ResponseEntity
                .ok(ApiResponse.success(usuarioService.listarPorNivel(nivelCuenta), "Usuarios por nivel encontrados"));
    }

    @GetMapping("/registrados-despues")
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> listarRegistradosDespuesDe(@RequestParam LocalDate fecha) {
        return ResponseEntity.ok(ApiResponse.success(usuarioService.listarRegistradosDespuesDe(fecha),
                "Usuarios registrados después de la fecha encontrados"));
    }

    @GetMapping("/buscar-por-fecha")
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> listarPorFecha(@RequestParam LocalDate fecha) {
        List<UsuarioDTO> usuarios = usuarioService.listarPorFechaVerificandoResultados(fecha);
        return ResponseEntity.ok(ApiResponse.success(usuarios, "Usuarios encontrados por fecha"));
    }

    // ─── Roles ────────────────────────────────────────────────────────────────

    @GetMapping("/roles")
    public ResponseEntity<ApiResponse<List<Rol>>> listarRoles() {
        return ResponseEntity.ok(ApiResponse.success(rolService.listarRoles(), "Roles listados exitosamente"));
    }

    @GetMapping("/roles/{idRol}")
    public ResponseEntity<ApiResponse<Rol>> buscarRolPorId(@PathVariable Long idRol) {
        return ResponseEntity.ok(ApiResponse.success(rolService.buscarPorId(idRol), "Rol encontrado"));
    }

    // ─── Estados ──────────────────────────────────────────────────────────────

    @GetMapping("/estados")
    public ResponseEntity<ApiResponse<List<EstadoUsuario>>> listarEstados() {
        return ResponseEntity
                .ok(ApiResponse.success(estadoUsuarioService.listarEstados(), "Estados listados exitosamente"));
    }

    @GetMapping("/estados/{idEstadoUsuario}")
    public ResponseEntity<ApiResponse<EstadoUsuario>> buscarEstadoPorId(@PathVariable Long idEstadoUsuario) {
        return ResponseEntity
                .ok(ApiResponse.success(estadoUsuarioService.buscarPorId(idEstadoUsuario), "Estado encontrado"));
    }
}
