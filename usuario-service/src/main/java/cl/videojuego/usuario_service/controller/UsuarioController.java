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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Usuarios",description = "Operaciones relacionadas con usuarios, roles y estados del sistema de videojuegos")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RolService rolService;
    private final EstadoUsuarioService estadoUsuarioService;

<<<<<<< Updated upstream
    // ─── Usuarios ─────────────────────────────────────────────────────────────
    @Operation(summary = "Listar todos los pagos", description = "Retorna una lista con todos los pagos registrados")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "")
=======

    //Listar Usuarios
    @Operation(summary = "Listar todos los usuarios",
            description = "Retorna una lista completa con todos los usuarios registrados en el sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuarios listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
>>>>>>> Stashed changes
    @GetMapping
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> listarUsuarios() {
        return ResponseEntity
                .ok(ApiResponse.success(usuarioService.listarUsuarios(), "Usuarios listados exitosamente"));
    }


    // Buscar  Usuario por ID

    @Operation(summary = "Buscar usuario por ID",
            description = "Obtiene los datos de un usuario específico mediante su identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{idUsuario}")
    public ResponseEntity<ApiResponse<UsuarioDTO>> buscarPorId(
            @Parameter(description = "Identificador único del usuario", example = "1",
            @PathVariable Long idUsuario) {
        return ResponseEntity.ok(ApiResponse.success(usuarioService.buscarPorId(idUsuario), "Usuario encontrado"));
    }



    // Registrar Nuevo Usuario
    @Operation(
            summary = "Registrar nuevo usuario",
            description = "Permite registrar un nuevo usuario en el sistema de videojuegos."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Usuario registrado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Rol o estado de usuario no encontrado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<UsuarioDTO>> registrarUsuario(@Valid @RequestBody UsuarioRegistroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(usuarioService.registrarUsuario(dto), "Usuario registrado exitosamente"));
    }


    // Actualizar Usuario

    @Operation(summary = "Actualizar usuario",
            description = "Permite modificar los datos de un usuario existente."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuario, rol o estado no encontrado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PutMapping("/{idUsuario}")
    public ResponseEntity<ApiResponse<UsuarioDTO>> actualizarUsuario(
            @PathVariable Long idUsuario,
            @Valid @RequestBody UsuarioRegistroDTO dto) {
        return ResponseEntity.ok(ApiResponse.success(usuarioService.actualizarUsuario(idUsuario, dto),
                "Usuario actualizado exitosamente"));
    }


    //Eliminar un Usuario del Sistema

    @Operation(summary = "Eliminar usuario",
             description = "Elimina un usuario del sistema mediante su identificador."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<ApiResponse<Void>> eliminarUsuario(@PathVariable Long idUsuario) {
        usuarioService.eliminarUsuario(idUsuario);
        return ResponseEntity.ok(ApiResponse.success(null, "Usuario eliminado exitosamente"));
    }



    // Actualizar Rol y Estado

    @Operation(summary = "Actualizar rol y estado de usuario",
               description = "Permite cambiar el rol y el estado de un usuario existente."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Rol y estado actualizados correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuario, rol o estado no encontrado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{idUsuario}/rol-estado")
    public ResponseEntity<ApiResponse<UsuarioDTO>> actualizarRolYEstado(
            @PathVariable Long idUsuario,
            @RequestParam Long idRol,
            @RequestParam Long idEstadoUsuario) {
        return ResponseEntity.ok(ApiResponse.success(
                usuarioService.actualizarRolYEstado(idUsuario, idRol, idEstadoUsuario), "Rol y estado actualizados"));
    }

    // ─── Búsquedas de usuarios ────────────────────────────────────────────────


    // Buscar Usuario por Correo

    @Operation(summary = "Buscar usuario por correo",
             description = "Busca un usuario utilizando su correo electrónico."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuario encontrado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/buscar-por-correo")
    public ResponseEntity<ApiResponse<UsuarioDTO>> buscarPorCorreo(
            @Parameter(description = "Correo electrónico del usuario", example = "lizz123@correo.cl")
            @RequestParam String correo) {
        return ResponseEntity.ok(ApiResponse.success(usuarioService.buscarPorCorreo(correo), "Usuario encontrado"));
    }


    //  Buscar Usuario por rol
    @Operation(
            summary = "Listar usuarios por rol",
            description = "Retorna todos los usuarios asociados a un rol específico."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuarios por rol obtenidos correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/rol/{idRol}")
<<<<<<< Updated upstream
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> listarPorRol(@PathVariable Long idRol) {
        return ResponseEntity
                .ok(ApiResponse.success(usuarioService.listarPorRol(idRol), "Usuarios por rol encontrados"));
=======
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> listarPorRol(
            @Parameter(description = "Identificador del rol", example = "1")
            @PathVariable Long idRol) {
        return ResponseEntity.ok(ApiResponse.success(usuarioService.listarPorRol(idRol), "Usuarios por rol encontrados"));
>>>>>>> Stashed changes
    }



    // Listar Usuario por Estado
    @Operation(
            summary = "Listar usuarios por estado",
            description = "Retorna todos los usuarios filtrados por estado."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuarios por estado obtenidos correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/estado/{idEstadoUsuario}")
<<<<<<< Updated upstream
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> listarPorEstado(@PathVariable Long idEstadoUsuario) {
        return ResponseEntity.ok(ApiResponse.success(usuarioService.listarPorEstado(idEstadoUsuario),
                "Usuarios por estado encontrados"));
=======
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> listarPorEstado(
            @Parameter(description = "Identificador del estado del usuario", example = "1")
            @PathVariable Long idEstadoUsuario) {
        return ResponseEntity.ok(ApiResponse.success(usuarioService.listarPorEstado(idEstadoUsuario), "Usuarios por estado encontrados"));
>>>>>>> Stashed changes
    }


   // Listar Usuario por nivel de cuenta
    @Operation(
            summary = "Listar usuarios por nivel de cuenta",
            description = "Retorna todos los usuarios que poseen un nivel de cuenta específico."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuarios por nivel obtenidos correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/nivel/{nivelCuenta}")
<<<<<<< Updated upstream
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> listarPorNivel(@PathVariable Integer nivelCuenta) {
        return ResponseEntity
                .ok(ApiResponse.success(usuarioService.listarPorNivel(nivelCuenta), "Usuarios por nivel encontrados"));
=======
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> listarPorNivel(
            @Parameter(description = "Nivel de cuenta del usuario", example = "10")
            @PathVariable Integer nivelCuenta) {
        return ResponseEntity.ok(ApiResponse.success(usuarioService.listarPorNivel(nivelCuenta), "Usuarios por nivel encontrados"));
>>>>>>> Stashed changes
    }


    // Registrar usuario despuies de una fecha
    @Operation(
            summary = "Listar usuarios registrados después de una fecha",
            description = "Retorna usuarios cuya fecha de registro sea posterior a la fecha indicada."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuarios encontrados correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Formato de fecha inválido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/registrados-despues")
<<<<<<< Updated upstream
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> listarRegistradosDespuesDe(@RequestParam LocalDate fecha) {
        return ResponseEntity.ok(ApiResponse.success(usuarioService.listarRegistradosDespuesDe(fecha),
                "Usuarios registrados después de la fecha encontrados"));
=======
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> listarRegistradosDespuesDe(
            @Parameter(description = "Fecha desde la cual se buscarán usuarios", example = "2026-06-01")
            @RequestParam LocalDate fecha) {
        return ResponseEntity.ok(ApiResponse.success(usuarioService.listarRegistradosDespuesDe(fecha), "Usuarios registrados después de la fecha encontrados"));
>>>>>>> Stashed changes
    }



    // Buscar Por fecha de Registro
    @Operation(
            summary = "Buscar usuarios por fecha de registro",
            description = "Retorna usuarios registrados exactamente en la fecha indicada."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuarios encontrados correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Formato de fecha inválido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "No hay usuarios registrados en esa fecha"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/buscar-por-fecha")
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> listarPorFecha(
            @Parameter(description = "Fecha exacta de registro", example = "2026-06-01")
            @RequestParam LocalDate fecha) {
        List<UsuarioDTO> usuarios = usuarioService.listarPorFechaVerificandoResultados(fecha);
        return ResponseEntity.ok(ApiResponse.success(usuarios, "Usuarios encontrados por fecha"));
    }

    // ─── Roles ────────────────────────────────────────────────────────────────

    //Listar por Roles
    @Operation(
            summary = "Listar roles",
            description = "Retorna todos los roles disponibles para los usuarios."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Roles listados correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/roles")
    public ResponseEntity<ApiResponse<List<Rol>>> listarRoles() {
        return ResponseEntity.ok(ApiResponse.success(rolService.listarRoles(), "Roles listados exitosamente"));
    }



    // Buscar Roll por ID
    @Operation(
            summary = "Buscar rol por ID",
            description = "Obtiene la información de un rol específico mediante su identificador."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Rol encontrado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Rol no encontrado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/roles/{idRol}")
    public ResponseEntity<ApiResponse<Rol>> buscarRolPorId(
            @Parameter(description = "Nuevo rol del usuario", example = "2")
            @PathVariable Long idRol) {
        return ResponseEntity.ok(ApiResponse.success(rolService.buscarPorId(idRol), "Rol encontrado"));
    }

    // ─── Estados ──────────────────────────────────────────────────────────────


    //Listar Usuarios ID
    @Operation(
            summary = "Listar estados de usuario",
            description = "Retorna todos los estados disponibles para los usuarios."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Estados listados correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/estados")
    public ResponseEntity<ApiResponse<List<EstadoUsuario>>> listarEstados() {
        return ResponseEntity
                .ok(ApiResponse.success(estadoUsuarioService.listarEstados(), "Estados listados exitosamente"));
    }


    // Buscar Estado de Usuario por id
    @Operation(
            summary = "Buscar estado de usuario por ID",
            description = "Obtiene la información de un estado de usuario específico mediante su identificador."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Estado encontrado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Estado no encontrado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/estados/{idEstadoUsuario}")
<<<<<<< Updated upstream
    public ResponseEntity<ApiResponse<EstadoUsuario>> buscarEstadoPorId(@PathVariable Long idEstadoUsuario) {
        return ResponseEntity
                .ok(ApiResponse.success(estadoUsuarioService.buscarPorId(idEstadoUsuario), "Estado encontrado"));
=======
    public ResponseEntity<ApiResponse<EstadoUsuario>> buscarEstadoPorId(
            @Parameter(description = "Nuevo estado del usuario", example = "1")
            @PathVariable Long idEstadoUsuario) {
        return ResponseEntity.ok(ApiResponse.success(estadoUsuarioService.buscarPorId(idEstadoUsuario), "Estado encontrado"));
>>>>>>> Stashed changes
    }
}
