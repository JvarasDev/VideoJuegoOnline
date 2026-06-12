package cl.videojuego.usuario_service.controller;

import cl.videojuego.usuario_service.dto.UsuarioDTO;
import cl.videojuego.usuario_service.dto.UsuarioRegistroDTO;
import cl.videojuego.usuario_service.model.EstadoUsuario;
import cl.videojuego.usuario_service.model.Rol;
import cl.videojuego.usuario_service.service.EstadoUsuarioService;
import cl.videojuego.usuario_service.service.RolService;
import cl.videojuego.usuario_service.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(usuarioService.buscarPorId(idUsuario));
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> registrarUsuario(@Valid @RequestBody UsuarioRegistroDTO dto) {
        UsuarioDTO creado = usuarioService.registrarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(
            @PathVariable Long idUsuario,
            @Valid @RequestBody UsuarioRegistroDTO dto
    ) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(idUsuario, dto));
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long idUsuario) {
        usuarioService.eliminarUsuario(idUsuario);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idUsuario}/rol-estado")
    public ResponseEntity<UsuarioDTO> actualizarRolYEstado(
            @PathVariable Long idUsuario,
            @RequestParam Long idRol,
            @RequestParam Long idEstadoUsuario
    ) {
        return ResponseEntity.ok(usuarioService.actualizarRolYEstado(idUsuario, idRol, idEstadoUsuario));
    }

    // ─── Búsquedas de usuarios ────────────────────────────────────────────────

    @GetMapping("/buscar-por-correo")
    public ResponseEntity<UsuarioDTO> buscarPorCorreo(@RequestParam String correo) {
        return ResponseEntity.ok(usuarioService.buscarPorCorreo(correo));
    }

    @GetMapping("/rol/{idRol}")
    public ResponseEntity<List<UsuarioDTO>> listarPorRol(@PathVariable Long idRol) {
        return ResponseEntity.ok(usuarioService.listarPorRol(idRol));
    }

    @GetMapping("/estado/{idEstadoUsuario}")
    public ResponseEntity<List<UsuarioDTO>> listarPorEstado(@PathVariable Long idEstadoUsuario) {
        return ResponseEntity.ok(usuarioService.listarPorEstado(idEstadoUsuario));
    }

    @GetMapping("/nivel/{nivelCuenta}")
    public ResponseEntity<List<UsuarioDTO>> listarPorNivel(@PathVariable Integer nivelCuenta) {
        return ResponseEntity.ok(usuarioService.listarPorNivel(nivelCuenta));
    }

    @GetMapping("/registrados-despues")
    public ResponseEntity<List<UsuarioDTO>> listarRegistradosDespuesDe(@RequestParam LocalDate fecha) {
        return ResponseEntity.ok(usuarioService.listarRegistradosDespuesDe(fecha));
    }

    @GetMapping("/buscar-por-fecha")
    public ResponseEntity<?> listarPorFecha(@RequestParam LocalDate fecha) {
        List<UsuarioDTO> usuarios = usuarioService.listarPorFecha(fecha);

        if (usuarios.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                    "mensaje", "No hay usuarios registrados en la fecha: " + fecha,
                    "usuarios", usuarios
            ));
        }

        return ResponseEntity.ok(usuarios);
    }

    // ─── Roles ────────────────────────────────────────────────────────────────

    @GetMapping("/roles")
    public ResponseEntity<List<Rol>> listarRoles() {
        return ResponseEntity.ok(rolService.listarRoles());
    }

    @GetMapping("/roles/{idRol}")
    public ResponseEntity<Rol> buscarRolPorId(@PathVariable Long idRol) {
        return ResponseEntity.ok(rolService.buscarPorId(idRol));
    }

    // ─── Estados ──────────────────────────────────────────────────────────────

    @GetMapping("/estados")
    public ResponseEntity<List<EstadoUsuario>> listarEstados() {
        return ResponseEntity.ok(estadoUsuarioService.listarEstados());
    }

    @GetMapping("/estados/{idEstadoUsuario}")
    public ResponseEntity<EstadoUsuario> buscarEstadoPorId(@PathVariable Long idEstadoUsuario) {
        return ResponseEntity.ok(estadoUsuarioService.buscarPorId(idEstadoUsuario));
    }
}
