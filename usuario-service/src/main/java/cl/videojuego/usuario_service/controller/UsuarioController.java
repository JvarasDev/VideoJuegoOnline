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
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(
        name = "Usuarios",
        description = "API para la gestión de usuarios, roles y estados del sistema de videojuegos"
)
public class UsuarioController {

        private final UsuarioService usuarioService;
        private final RolService rolService;
        private final EstadoUsuarioService estadoUsuarioService;

        @Operation(
                summary = "Listar usuarios",
                description = "Retorna una lista completa con todos los usuarios registrados en el sistema."
        )
        @ApiResponses({
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Usuarios listados correctamente",
                        content = @Content(
                                mediaType = "application/json",
                                array = @ArraySchema(
                                        schema = @Schema(implementation = UsuarioDTO.class)
                                )
                        )
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content
                )
        })
        @GetMapping
        public ResponseEntity<ApiResponsev1<List<UsuarioDTO>>> listarUsuarios() {
                return ResponseEntity.ok(
                        ApiResponsev1.success(usuarioService.listarUsuarios(), "Usuarios listados exitosamente")
                );
        }

        @Operation(
                summary = "Buscar usuario por ID",
                description = "Obtiene la información detallada de un usuario mediante su identificador único."
        )
        @ApiResponses({
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Usuario encontrado correctamente",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = UsuarioDTO.class)
                        )
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "404",
                        description = "Usuario no encontrado",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content
                )
        })
        @GetMapping("/{idUsuario}")
        public ResponseEntity<ApiResponsev1<UsuarioDTO>> buscarPorId(
                @Parameter(
                        description = "Identificador único del usuario",
                        example = "1"
                )
                @PathVariable Long idUsuario
        ) {
                return ResponseEntity.ok(
                        ApiResponsev1.success(usuarioService.buscarPorId(idUsuario), "Usuario encontrado")
                );
        }

        @Operation(
                summary = "Registrar usuario",
                description = "Permite registrar un nuevo usuario en el sistema, asociándolo a un rol y estado existente."
        )
        @ApiResponses({
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "201",
                        description = "Usuario registrado correctamente",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = UsuarioDTO.class)
                        )
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "400",
                        description = "Datos inválidos enviados en la solicitud",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "404",
                        description = "Rol o estado de usuario no encontrado",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content
                )
        })
        @PostMapping
        public ResponseEntity<ApiResponsev1<UsuarioDTO>> registrarUsuario(
                @Valid @RequestBody UsuarioRegistroDTO dto
        ) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ApiResponsev1.success(
                                usuarioService.registrarUsuario(dto),
                                "Usuario registrado exitosamente"
                        ));
        }

        @Operation(
                summary = "Actualizar usuario",
                description = "Permite modificar la información de un usuario existente."
        )
        @ApiResponses({
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Usuario actualizado correctamente",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = UsuarioDTO.class)
                        )
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "400",
                        description = "Datos inválidos enviados en la solicitud",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "404",
                        description = "Usuario, rol o estado no encontrado",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content
                )
        })
        @PutMapping("/{idUsuario}")
        public ResponseEntity<ApiResponsev1<UsuarioDTO>> actualizarUsuario(
                @Parameter(
                        description = "Identificador único del usuario",
                        example = "1"
                )
                @PathVariable Long idUsuario,

                @Valid @RequestBody UsuarioRegistroDTO dto
        ) {
                return ResponseEntity.ok(
                        ApiResponsev1.success(
                                usuarioService.actualizarUsuario(idUsuario, dto),
                                "Usuario actualizado exitosamente"
                        )
                );
        }

        @Operation(
                summary = "Eliminar usuario",
                description = "Elimina un usuario existente mediante su identificador."
        )
        @ApiResponses({
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Usuario eliminado correctamente",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "404",
                        description = "Usuario no encontrado",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content
                )
        })
        @DeleteMapping("/{idUsuario}")
        public ResponseEntity<ApiResponsev1<Void>> eliminarUsuario(
                @Parameter(
                        description = "Identificador único del usuario",
                        example = "1"
                )
                @PathVariable Long idUsuario
        ) {
                usuarioService.eliminarUsuario(idUsuario);
                return ResponseEntity.ok(
                        ApiResponsev1.success(null, "Usuario eliminado exitosamente")
                );
        }

        @Operation(
                summary = "Actualizar rol y estado",
                description = "Permite modificar el rol y el estado de un usuario existente."
        )
        @ApiResponses({
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Rol y estado actualizados correctamente",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = UsuarioDTO.class)
                        )
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "404",
                        description = "Usuario, rol o estado no encontrado",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content
                )
        })
        @PutMapping("/{idUsuario}/rol-estado")
        public ResponseEntity<ApiResponsev1<UsuarioDTO>> actualizarRolYEstado(
                @Parameter(
                        description = "Identificador único del usuario",
                        example = "1"
                )
                @PathVariable Long idUsuario,

                @Parameter(
                        description = "Nuevo identificador del rol",
                        example = "2"
                )
                @RequestParam Long idRol,

                @Parameter(
                        description = "Nuevo identificador del estado del usuario",
                        example = "1"
                )
                @RequestParam Long idEstadoUsuario
        ) {
                return ResponseEntity.ok(
                        ApiResponsev1.success(
                                usuarioService.actualizarRolYEstado(idUsuario, idRol, idEstadoUsuario),
                                "Rol y estado actualizados"
                        )
                );
        }

        @Operation(
                summary = "Buscar usuario por correo",
                description = "Busca un usuario registrado utilizando su correo electrónico."
        )
        @ApiResponses({
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Usuario encontrado correctamente",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = UsuarioDTO.class)
                        )
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "404",
                        description = "Usuario no encontrado",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content
                )
        })
        @GetMapping("/buscar-por-correo")
        public ResponseEntity<ApiResponsev1<UsuarioDTO>> buscarPorCorreo(
                @Parameter(
                        description = "Correo electrónico del usuario",
                        example = "jugador@correo.cl"
                )
                @RequestParam String correo
        ) {
                return ResponseEntity.ok(
                        ApiResponsev1.success(usuarioService.buscarPorCorreo(correo), "Usuario encontrado")
                );
        }

        @Operation(
                summary = "Listar usuarios por rol",
                description = "Retorna todos los usuarios asociados a un rol específico."
        )
        @ApiResponses({
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Usuarios por rol encontrados",
                        content = @Content(
                                mediaType = "application/json",
                                array = @ArraySchema(
                                        schema = @Schema(implementation = UsuarioDTO.class)
                                )
                        )
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content
                )
        })
        @GetMapping("/rol/{idRol}")
        public ResponseEntity<ApiResponsev1<List<UsuarioDTO>>> listarPorRol(
                @Parameter(
                        description = "Identificador del rol",
                        example = "1"
                )
                @PathVariable Long idRol
        ) {
                return ResponseEntity.ok(
                        ApiResponsev1.success(usuarioService.listarPorRol(idRol), "Usuarios por rol encontrados")
                );
        }

        @Operation(
                summary = "Listar usuarios por estado",
                description = "Retorna todos los usuarios asociados a un estado específico."
        )
        @ApiResponses({
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Usuarios por estado encontrados",
                        content = @Content(
                                mediaType = "application/json",
                                array = @ArraySchema(
                                        schema = @Schema(implementation = UsuarioDTO.class)
                                )
                        )
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content
                )
        })
        @GetMapping("/estado/{idEstadoUsuario}")
        public ResponseEntity<ApiResponsev1<List<UsuarioDTO>>> listarPorEstado(
                @Parameter(
                        description = "Identificador del estado del usuario",
                        example = "1"
                )
                @PathVariable Long idEstadoUsuario
        ) {
                return ResponseEntity.ok(
                        ApiResponsev1.success(usuarioService.listarPorEstado(idEstadoUsuario), "Usuarios por estado encontrados")
                );
        }

        @Operation(
                summary = "Listar usuarios por nivel de cuenta",
                description = "Retorna todos los usuarios que poseen un nivel de cuenta específico."
        )
        @ApiResponses({
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Usuarios por nivel encontrados",
                        content = @Content(
                                mediaType = "application/json",
                                array = @ArraySchema(
                                        schema = @Schema(implementation = UsuarioDTO.class)
                                )
                        )
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content
                )
        })
        @GetMapping("/nivel/{nivelCuenta}")
        public ResponseEntity<ApiResponsev1<List<UsuarioDTO>>> listarPorNivel(
                @Parameter(
                        description = "Nivel de cuenta del usuario",
                        example = "1"
                )
                @PathVariable Integer nivelCuenta
        ) {
                return ResponseEntity.ok(
                        ApiResponsev1.success(usuarioService.listarPorNivel(nivelCuenta), "Usuarios por nivel encontrados")
                );
        }

        @Operation(
                summary = "Listar usuarios registrados después de una fecha",
                description = "Retorna usuarios cuya fecha de registro sea posterior a la fecha indicada."
        )
        @ApiResponses({
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Usuarios encontrados correctamente",
                        content = @Content(
                                mediaType = "application/json",
                                array = @ArraySchema(
                                        schema = @Schema(implementation = UsuarioDTO.class)
                                )
                        )
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "400",
                        description = "Formato de fecha inválido",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content
                )
        })
        @GetMapping("/registrados-despues")
        public ResponseEntity<ApiResponsev1<List<UsuarioDTO>>> listarRegistradosDespuesDe(
                @Parameter(
                        description = "Fecha mínima de registro en formato ISO yyyy-MM-dd",
                        example = "2026-06-01"
                )
                @RequestParam LocalDate fecha
        ) {
                return ResponseEntity.ok(
                        ApiResponsev1.success(
                                usuarioService.listarRegistradosDespuesDe(fecha),
                                "Usuarios registrados después de la fecha encontrados"
                        )
                );
        }

        @Operation(
                summary = "Buscar usuarios por fecha de registro",
                description = "Retorna usuarios registrados exactamente en la fecha indicada."
        )
        @ApiResponses({
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Usuarios encontrados por fecha",
                        content = @Content(
                                mediaType = "application/json",
                                array = @ArraySchema(
                                        schema = @Schema(implementation = UsuarioDTO.class)
                                )
                        )
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "400",
                        description = "Formato de fecha inválido",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "404",
                        description = "No hay usuarios registrados en la fecha indicada",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content
                )
        })
        @GetMapping("/buscar-por-fecha")
        public ResponseEntity<ApiResponsev1<List<UsuarioDTO>>> listarPorFecha(
                @Parameter(
                        description = "Fecha exacta de registro en formato ISO yyyy-MM-dd",
                        example = "2026-06-01"
                )
                @RequestParam LocalDate fecha
        ) {
                List<UsuarioDTO> usuarios = usuarioService.listarPorFechaVerificandoResultados(fecha);
                return ResponseEntity.ok(
                        ApiResponsev1.success(usuarios, "Usuarios encontrados por fecha")
                );
        }

        @Operation(
                summary = "Listar roles",
                description = "Retorna todos los roles disponibles para los usuarios del sistema."
        )
        @ApiResponses({
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Roles listados correctamente",
                        content = @Content(
                                mediaType = "application/json",
                                array = @ArraySchema(
                                        schema = @Schema(implementation = Rol.class)
                                )
                        )
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content
                )
        })
        @GetMapping("/roles")
        public ResponseEntity<ApiResponsev1<List<Rol>>> listarRoles() {
                return ResponseEntity.ok(
                        ApiResponsev1.success(rolService.listarRoles(), "Roles listados exitosamente")
                );
        }

        @Operation(
                summary = "Buscar rol por ID",
                description = "Obtiene la información de un rol específico mediante su identificador."
        )
        @ApiResponses({
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Rol encontrado correctamente",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = Rol.class)
                        )
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "404",
                        description = "Rol no encontrado",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content
                )
        })
        @GetMapping("/roles/{idRol}")
        public ResponseEntity<ApiResponsev1<Rol>> buscarRolPorId(
                @Parameter(
                        description = "Identificador único del rol",
                        example = "1"
                )
                @PathVariable Long idRol
        ) {
                return ResponseEntity.ok(
                        ApiResponsev1.success(rolService.buscarPorId(idRol), "Rol encontrado")
                );
        }

        @Operation(
                summary = "Listar estados de usuario",
                description = "Retorna todos los estados disponibles para los usuarios del sistema."
        )
        @ApiResponses({
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Estados listados correctamente",
                        content = @Content(
                                mediaType = "application/json",
                                array = @ArraySchema(
                                        schema = @Schema(implementation = EstadoUsuario.class)
                                )
                        )
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content
                )
        })
        @GetMapping("/estados")
        public ResponseEntity<ApiResponsev1<List<EstadoUsuario>>> listarEstados() {
                return ResponseEntity.ok(
                        ApiResponsev1.success(estadoUsuarioService.listarEstados(), "Estados listados exitosamente")
                );
        }

        @Operation(
                summary = "Buscar estado de usuario por ID",
                description = "Obtiene la información de un estado de usuario específico mediante su identificador."
        )
        @ApiResponses({
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Estado encontrado correctamente",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = EstadoUsuario.class)
                        )
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "404",
                        description = "Estado no encontrado",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content
                )
        })
        @GetMapping("/estados/{idEstadoUsuario}")
        public ResponseEntity<ApiResponsev1<EstadoUsuario>> buscarEstadoPorId(
                @Parameter(
                        description = "Identificador único del estado de usuario",
                        example = "1"
                )
                @PathVariable Long idEstadoUsuario
        ) {
                return ResponseEntity.ok(
                        ApiResponsev1.success(estadoUsuarioService.buscarPorId(idEstadoUsuario), "Estado encontrado")
                );
        }
}