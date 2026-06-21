package cl.videojuego.usuario_service.controller;

import cl.videojuego.usuario_service.dto.GlobalResponse;
import cl.videojuego.usuario_service.dto.LoginRequestDTO;
import cl.videojuego.usuario_service.dto.AuthResponseDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios",description = "Operaciones relacionadas con usuarios, roles y estados del sistema de videojuegos")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RolService rolService;
    private final EstadoUsuarioService estadoUsuarioService;

    public UsuarioController(UsuarioService usuarioService, RolService rolService, EstadoUsuarioService estadoUsuarioService) {
        this.usuarioService = usuarioService;
        this.rolService = rolService;
        this.estadoUsuarioService = estadoUsuarioService;
    }

    // Listar Usuarios
    @Operation(summary = "Listar todos los usuarios", description = "Retorna una lista completa con todos los usuarios registrados en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<GlobalResponse<List<UsuarioDTO>>> listarUsuarios() {
        return ResponseEntity.ok(GlobalResponse.success(usuarioService.listarUsuarios(), "Usuarios listados exitosamente"));
    }

    // Buscar Usuario por ID
    @Operation(summary = "Buscar usuario por ID", description = "Obtiene los datos de un usuario especifico mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{idUsuario}")
    public ResponseEntity<GlobalResponse<UsuarioDTO>> buscarPorId(@Parameter(description = "Identificador unico del usuario", example = "1") @PathVariable Long idUsuario) {
        return ResponseEntity.ok(GlobalResponse.success(usuarioService.buscarPorId(idUsuario), "Usuario encontrado"));
    }

    // Registrar Nuevo Usuario
    @Operation(summary = "Registrar nuevo usuario", description = "Permite registrar un nuevo usuario en el sistema de videojuegos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "404", description = "Rol o estado de usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<GlobalResponse<UsuarioDTO>> registrarUsuario(@Valid @RequestBody UsuarioRegistroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(GlobalResponse.success(usuarioService.registrarUsuario(dto), "Usuario registrado exitosamente"));
    }

    // Actualizar Usuario
    @Operation(summary = "Actualizar usuario", description = "Permite modificar los datos de un usuario existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "404", description = "Usuario, rol o estado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{idUsuario}")
    public ResponseEntity<GlobalResponse<UsuarioDTO>> actualizarUsuario(@Parameter(description = "Identificador del usuario", example = "1") @PathVariable Long idUsuario, @Valid @RequestBody UsuarioRegistroDTO dto) {
        return ResponseEntity.ok(GlobalResponse.success(usuarioService.actualizarUsuario(idUsuario, dto), "Usuario actualizado exitosamente"));
    }

    // Eliminar (Inactivar) Usuario
    @Operation(summary = "Eliminar (inactivar) usuario", description = "Cambia el estado del usuario a inactivo en lugar de eliminarlo fsicamente de la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario inactivado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> eliminarUsuario(@Parameter(description = "Identificador del usuario a inactivar", example = "1") @PathVariable Long idUsuario) {
        usuarioService.eliminarUsuario(idUsuario);
        return ResponseEntity.noContent().build();
    }



    // Listar por Roles
    @Operation(summary = "Listar roles", description = "Retorna todos los roles disponibles para los usuarios.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/roles")
    public ResponseEntity<GlobalResponse<List<Rol>>> listarRoles() {
        return ResponseEntity.ok(GlobalResponse.success(rolService.listarRoles(), "Roles listados exitosamente"));
    }

    // Buscar Rol por ID
    @Operation(summary = "Buscar rol por ID", description = "Obtiene la informacion de un rol especifico mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/roles/{idRol}")
    public ResponseEntity<GlobalResponse<Rol>> buscarRolPorId(@Parameter(description = "Nuevo rol del usuario", example = "2") @PathVariable Long idRol) {
        return ResponseEntity.ok(GlobalResponse.success(rolService.buscarPorId(idRol), "Rol encontrado"));
    }

    // Listar Estados
    @Operation(summary = "Listar estados de usuario", description = "Retorna todos los estados disponibles para los usuarios.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estados listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/estados")
    public ResponseEntity<GlobalResponse<List<EstadoUsuario>>> listarEstados() {
        return ResponseEntity.ok(GlobalResponse.success(estadoUsuarioService.listarEstados(), "Estados listados exitosamente"));
    }

    // Buscar Estado de Usuario por id
    @Operation(summary = "Buscar estado de usuario por ID", description = "Obtiene la informacion de un estado de usuario especifico mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/estados/{idEstadoUsuario}")
    public ResponseEntity<GlobalResponse<EstadoUsuario>> buscarEstadoPorId(@Parameter(description = "Nuevo estado del usuario", example = "1") @PathVariable Long idEstadoUsuario) {
        return ResponseEntity.ok(GlobalResponse.success(estadoUsuarioService.buscarPorId(idEstadoUsuario), "Estado encontrado"));
    }
}
