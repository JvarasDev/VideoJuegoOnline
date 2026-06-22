package cl.videojuego.usuario_service.controller;

import cl.videojuego.usuario_service.dto.ApiResponsev1;
import cl.videojuego.usuario_service.dto.UsuarioDTO;
import cl.videojuego.usuario_service.dto.UsuarioRegistroDTO;
import cl.videojuego.usuario_service.model.EstadoUsuario;
import cl.videojuego.usuario_service.model.Rol;
import cl.videojuego.usuario_service.service.EstadoUsuarioService;
import cl.videojuego.usuario_service.service.RolService;
import cl.videojuego.usuario_service.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios, roles y estados del sistema de videojuegos")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RolService rolService;
    private final EstadoUsuarioService estadoUsuarioService;

    @Operation(summary = "Listar todos los usuarios", description = "Retorna una lista completa con todos los usuarios registrados en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<ApiResponsev1<List<UsuarioDTO>>> listarUsuarios() {
        return ResponseEntity
                .ok(ApiResponsev1.success(usuarioService.listarUsuarios(), "Usuarios listados exitosamente"));
    }

    @Operation(summary = "Buscar usuario por ID", description = "Obtiene los datos de un usuario específico mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{idUsuario}")
    public ResponseEntity<ApiResponsev1<UsuarioDTO>> buscarPorId(
            @Parameter(description = "Identificador único del usuario", example = "1")
            @PathVariable Long idUsuario
    ) {
        return ResponseEntity.ok(ApiResponsev1.success(usuarioService.buscarPorId(idUsuario), "Usuario encontrado"));
    }

    @Operation(summary = "Registrar nuevo usuario", description = "Permite registrar un nuevo usuario en el sistema de videojuegos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Rol o estado de usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<ApiResponsev1<UsuarioDTO>> registrarUsuario(@Valid @RequestBody UsuarioRegistroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponsev1.success(usuarioService.registrarUsuario(dto), "Usuario registrado exitosamente"));
    }

    @Operation(summary = "Actualizar usuario", description = "Permite modificar los datos de un usuario existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario, rol o estado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{idUsuario}")
    public ResponseEntity<ApiResponsev1<UsuarioDTO>> actualizarUsuario(
            @PathVariable Long idUsuario,
            @Valid @RequestBody UsuarioRegistroDTO dto
    ) {
        return ResponseEntity.ok(ApiResponsev1.success(usuarioService.actualizarUsuario(idUsuario, dto),
                "Usuario actualizado exitosamente"));
    }

    @Operation(summary = "Eliminar (inactivar) usuario", description = "Cambia el estado del usuario a inactivo en lugar de eliminarlo físicamente de la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario inactivado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<ApiResponsev1<Void>> eliminarUsuario(@PathVariable Long idUsuario) {
        usuarioService.eliminarUsuario(idUsuario);
        return ResponseEntity.ok(ApiResponsev1.success(null, "Usuario eliminado exitosamente"));
    }

    @PutMapping("/{idUsuario}/rol-estado")
    public ResponseEntity<ApiResponsev1<UsuarioDTO>> actualizarRolYEstado(
            @PathVariable Long idUsuario,
            @RequestParam Long idRol,
            @RequestParam Long idEstadoUsuario
    ) {
        return ResponseEntity.ok(ApiResponsev1.success(
                usuarioService.actualizarRolYEstado(idUsuario, idRol, idEstadoUsuario),
                "Rol y estado actualizados"));
    }

    @Operation(summary = "Buscar usuario por correo", description = "Busca un usuario utilizando su correo electrónico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/buscar-por-correo")
    public ResponseEntity<ApiResponsev1<UsuarioDTO>> buscarPorCorreo(
            @Parameter(description = "Correo electrónico del usuario", example = "lizz123@correo.cl")
            @RequestParam String correo
    ) {
        return ResponseEntity.ok(ApiResponsev1.success(usuarioService.buscarPorCorreo(correo), "Usuario encontrado"));
    }

    @Operation(summary = "Listar usuarios por rol", description = "Retorna todos los usuarios asociados a un rol específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuarios por rol obtenidos correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/rol/{idRol}")
    public ResponseEntity<ApiResponsev1<List<UsuarioDTO>>> listarPorRol(
            @Parameter(description = "Identificador del rol", example = "1")
            @PathVariable Long idRol
    ) {
        return ResponseEntity.ok(ApiResponsev1.success(usuarioService.listarPorRol(idRol), "Usuarios por rol encontrados"));
    }

    @Operation(summary = "Listar usuarios por estado", description = "Retorna todos los usuarios filtrados por estado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuarios por estado obtenidos correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/estado/{idEstadoUsuario}")
    public ResponseEntity<ApiResponsev1<List<UsuarioDTO>>> listarPorEstado(
            @Parameter(description = "Identificador del estado del usuario", example = "1")
            @PathVariable Long idEstadoUsuario
    ) {
        return ResponseEntity.ok(ApiResponsev1.success(usuarioService.listarPorEstado(idEstadoUsuario), "Usuarios por estado encontrados"));
    }

    @Operation(summary = "Listar usuarios por nivel de cuenta", description = "Retorna todos los usuarios que poseen un nivel de cuenta específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuarios por nivel obtenidos correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/nivel/{nivelCuenta}")
    public ResponseEntity<ApiResponsev1<List<UsuarioDTO>>> listarPorNivel(
            @Parameter(description = "Nivel de cuenta del usuario", example = "10")
            @PathVariable Integer nivelCuenta
    ) {
        return ResponseEntity.ok(ApiResponsev1.success(usuarioService.listarPorNivel(nivelCuenta), "Usuarios por nivel encontrados"));
    }

    @Operation(summary = "Listar usuarios registrados después de una fecha", description = "Retorna usuarios cuya fecha de registro sea posterior a la fecha indicada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuarios encontrados correctamente"),
            @ApiResponse(responseCode = "400", description = "Formato de fecha inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/registrados-despues")
    public ResponseEntity<ApiResponsev1<List<UsuarioDTO>>> listarRegistradosDespuesDe(
            @Parameter(description = "Fecha desde la cual se buscarán usuarios", example = "2026-06-01")
            @RequestParam LocalDate fecha
    ) {
        return ResponseEntity.ok(ApiResponsev1.success(usuarioService.listarRegistradosDespuesDe(fecha),
                "Usuarios registrados después de la fecha encontrados"));
    }

    @Operation(summary = "Buscar usuarios por fecha de registro", description = "Retorna usuarios registrados exactamente en la fecha indicada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuarios encontrados correctamente"),
            @ApiResponse(responseCode = "400", description = "Formato de fecha inválido"),
            @ApiResponse(responseCode = "404", description = "No hay usuarios registrados en esa fecha"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/buscar-por-fecha")
    public ResponseEntity<ApiResponsev1<List<UsuarioDTO>>> listarPorFecha(
            @Parameter(description = "Fecha exacta de registro", example = "2026-06-01")
            @RequestParam LocalDate fecha
    ) {
        List<UsuarioDTO> usuarios = usuarioService.listarPorFechaVerificandoResultados(fecha);
        return ResponseEntity.ok(ApiResponsev1.success(usuarios, "Usuarios encontrados por fecha"));
    }

    @Operation(summary = "Listar roles", description = "Retorna todos los roles disponibles para los usuarios.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Roles listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/roles")
    public ResponseEntity<ApiResponsev1<List<Rol>>> listarRoles() {
        return ResponseEntity.ok(ApiResponsev1.success(rolService.listarRoles(), "Roles listados exitosamente"));
    }

    @Operation(summary = "Buscar rol por ID", description = "Obtiene la información de un rol específico mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/roles/{idRol}")
    public ResponseEntity<ApiResponsev1<Rol>> buscarRolPorId(
            @Parameter(description = "Nuevo rol del usuario", example = "2")
            @PathVariable Long idRol
    ) {
        return ResponseEntity.ok(ApiResponsev1.success(rolService.buscarPorId(idRol), "Rol encontrado"));
    }

    @Operation(summary = "Listar estados de usuario", description = "Retorna todos los estados disponibles para los usuarios.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estados listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/estados")
    public ResponseEntity<ApiResponsev1<List<EstadoUsuario>>> listarEstados() {
        return ResponseEntity
                .ok(ApiResponsev1.success(estadoUsuarioService.listarEstados(), "Estados listados exitosamente"));
    }

    @Operation(summary = "Buscar estado de usuario por ID", description = "Obtiene la información de un estado de usuario específico mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/estados/{idEstadoUsuario}")
    public ResponseEntity<ApiResponsev1<EstadoUsuario>> buscarEstadoPorId(
            @Parameter(description = "Nuevo estado del usuario", example = "1")
            @PathVariable Long idEstadoUsuario
    ) {
        return ResponseEntity.ok(ApiResponsev1.success(estadoUsuarioService.buscarPorId(idEstadoUsuario), "Estado encontrado"));
    }
}
