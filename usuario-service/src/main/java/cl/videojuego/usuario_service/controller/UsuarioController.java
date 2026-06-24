package cl.videojuego.usuario_service.controller;

import cl.videojuego.usuario_service.dto.UsuarioDTO;
import cl.videojuego.usuario_service.dto.UsuarioRegistroDTO;
import cl.videojuego.usuario_service.dto.ApiResponsev1;
import cl.videojuego.usuario_service.model.EstadoUsuario;
import cl.videojuego.usuario_service.model.Rol;
import cl.videojuego.usuario_service.service.EstadoUsuarioService;
import cl.videojuego.usuario_service.service.RolService;
import cl.videojuego.usuario_service.service.UsuarioService;
import cl.videojuego.usuario_service.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@Tag(name = "Usuarios", description = "Operaciones CRUD para la gestión completa de usuarios, roles y estados de cuenta en el sistema de videojuegos.")
public class UsuarioController {

        private final UsuarioService usuarioService;
        private final RolService rolService;
        private final EstadoUsuarioService estadoUsuarioService;

        @Operation(summary = "Listar todos los usuarios", description = "Retorna una lista completa con todos los usuarios registrados en el sistema.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Usuarios listados correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponsev1.class), examples = @ExampleObject(value = """
                                            {
                                        "status": 200,
                                        "message": "Usuarios listados exitosamente",
                                        "data": [
                                        {
                                        "idUsuario": 1,
                                        "nombre": "Elizabeth",
                                        "apellido": "Reyes",
                                        "correo": "bethdemo@gmail.com",
                                        "nivelCuenta": 5,
                                        "nombreRol": "ADMIN",
                                        "nombreEstado": "ACTIVO"
                                        },
                                        {
                                        "idUsuario": 2,
                                        "nombre": "Scarlett",
                                        "apellido": "Riquelme",
                                        "correo": "scarlettdemo@gmail.com",
                                        "nivelCuenta": 4,
                                        "nombreRol": "ADMIN",
                                        "nombreEstado": "ACTIVO"
                                        }
                                            """))),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 500,
                                          "error": "Internal Server Error",
                                          "message": "Error inesperado T_T",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """)))
        })
        @GetMapping
        public ResponseEntity<ApiResponsev1<List<UsuarioDTO>>> listarUsuarios() {
                return ResponseEntity.ok(ApiResponsev1.success(usuarioService.listarUsuarios(),
                                "Usuarios listados exitosamente"));
        }

        @Operation(summary = "Buscar usuario por ID", description = "Obtiene los datos detallados de un usuario específico buscando mediante su identificador único (ID).")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponsev1.class), examples = @ExampleObject(value = """
                                                   {
                                                "status": 200,
                                                "message": "Usuario encontrado exitosamente",
                                                "data": {

                                                "idUsuario": 1,
                                                "nombre": "Elizabeth",
                                                "apellido": "Reyes",
                                                "correo": "bethdemo@gmail.com",
                                                "nivelCuenta": 5,
                                                "nombreRol": "ADMIN",
                                                "nombreEstado": "ACTIVO"
                                                },
                                                "timestamp": "2026-06-24T04:12:58"
                                        }

                                                    """))),
                        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 404,
                                          "error": "Not Found",
                                          "message": "Usuario con ID 99 no fue encontrado en el sistema",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """))),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 500,
                                          "error": "Internal Server Error",
                                          "message": "Error inesperado T_T",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """)))
        })
        @GetMapping("/{idUsuario}")
        public ResponseEntity<ApiResponsev1<UsuarioDTO>> buscarUsuarioPorId(
                        @Parameter(description = "Identificador único del usuario", example = "1") @PathVariable Long idUsuario) {
                return ResponseEntity.ok(ApiResponsev1.success(usuarioService.buscarPorId(idUsuario),
                                "Usuario encontrado exitosamente"));
        }

        @Operation(summary = "Registrar nuevo usuario", description = "Permite registrar un nuevo usuario en el sistema de videojuegos. Realiza validaciones de formato de correo y longitud de contraseñas.")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Usuario registrado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponsev1.class), examples = @ExampleObject(value = """
                                                            {
                                                                "status": 200,
                                                                "message": "Usuario registrado exitosamente",
                                                                "data": {
                                                                "idUsuario": 11,
                                                                "nombre": "Juan",
                                                                "apellido": "Varas",
                                                        "correo": "jvaras@admin.com",
                                                        "nivelCuenta": 1,
                                                        "nombreRol": "ADMIN",
                                                        "nombreEstado": "ACTIVO"
                                                        },
                                                        "timestamp": "2026-06-24T04:16:27"
                                        }

                                        }

                                                }
                                                            """))),
                        @ApiResponse(responseCode = "400", description = "Datos inválidos (ej. correo mal formado o campos vacíos)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 400,
                                          "error": "Validation Error",
                                          "message": "El correo debe tener un formato válido",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """))),
                        @ApiResponse(responseCode = "404", description = "Rol o estado de usuario proporcionado no fue encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 404,
                                          "error": "Not Found",
                                          "message": "Rol con ID 5 no encontrado",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """))),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 500,
                                          "error": "Internal Server Error",
                                          "message": "Error inesperado T_T",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """)))
        })
        @PostMapping
        public ResponseEntity<ApiResponsev1<UsuarioDTO>> registrarUsuario(
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto con los datos del nuevo usuario a registrar", required = true) @Valid @RequestBody UsuarioRegistroDTO dto) {
                return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponsev1
                                .success(usuarioService.registrarUsuario(dto), "Usuario registrado exitosamente"));
        }

        @Operation(summary = "Actualizar usuario", description = "Permite modificar los datos de un usuario existente. Debe enviarse el objeto completo con los datos actualizados.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
                        @ApiResponse(responseCode = "400", description = "Datos inválidos (ej. contraseñas muy cortas o campos nulos)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Usuario, rol o estado no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 404,
                                          "error": "Not Found",
                                          "message": "Usuario con ID 1 no encontrado",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """))),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @PutMapping("/{idUsuario}")
        public ResponseEntity<ApiResponsev1<UsuarioDTO>> actualizarUsuario(
                        @Parameter(description = "Identificador único del usuario a actualizar", example = "1") @PathVariable Long idUsuario,
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto con los datos actualizados del usuario", required = true) @Valid @RequestBody UsuarioRegistroDTO dto) {
                return ResponseEntity.ok(ApiResponsev1.success(usuarioService.actualizarUsuario(idUsuario, dto),
                                "Usuario actualizado exitosamente"));
        }

        @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema mediante su identificador. La eliminación podría estar restringida si el usuario tiene datos asociados.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponsev1.class), examples = @ExampleObject(value = """
                                        {
                                        "status": 200,
                                        "message": "Usuario eliminado exitosamente",
                                        "timestamp": "2026-06-24T04:20:28"
                                        }
                                        """))),
                        @ApiResponse(responseCode = "404", description = "Usuario a eliminar no fue encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 404,
                                          "error": "Not Found",
                                          "message": "Usuario con ID 1 no encontrado",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """))),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 500,
                                          "error": "Internal Server Error",
                                          "message": "Error inesperado T_T",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """)))
        })
        @DeleteMapping("/{idUsuario}")
        public ResponseEntity<ApiResponsev1<Void>> eliminarUsuario(
                        @Parameter(description = "Identificador único del usuario a eliminar", example = "1") @PathVariable Long idUsuario) {
                usuarioService.eliminarUsuario(idUsuario);
                return ResponseEntity.ok(ApiResponsev1.success(null, "Usuario eliminado exitosamente"));
        }

        @Operation(summary = "Actualizar rol y estado de usuario", description = "Permite cambiar únicamente el rol y el estado de un usuario existente sin modificar sus demás datos personales.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Rol y estado actualizados correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponsev1.class), examples = @ExampleObject(value = """
                                                    {
                                                "status": 200,
                                                "message": "Usuario actualizado exitosamente",
                                                "data": {
                                                "idUsuario": 1,
                                                "nombre": "Elizabeth",
                                                "apellido": "Reyes",
                                                "correo": "bethdemo@gmail.com",
                                                "nivelCuenta": 5,
                                                "nombreRol": "ADMIN",
                                                "nombreEstado": "ACTIVO"
                                                },
                                                "timestamp": "2026-06-24T04:38:43"
                                        }
                                                    """))),
                        @ApiResponse(responseCode = "404", description = "Usuario, rol o estado no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 404,
                                          "error": "Not Found",
                                          "message": "Rol con ID 10 no encontrado",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """))),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @PutMapping("/{idUsuario}/rol-estado")
        public ResponseEntity<ApiResponsev1<UsuarioDTO>> actualizarRolYEstado(
                        @Parameter(description = "Identificador único del usuario", example = "1") @PathVariable Long idUsuario,
                        @Parameter(description = "Identificador del nuevo rol", example = "2") @RequestParam Long idRol,
                        @Parameter(description = "Identificador del nuevo estado", example = "1") @RequestParam Long idEstadoUsuario) {
                return ResponseEntity.ok(ApiResponsev1.success(
                                usuarioService.actualizarRolYEstado(idUsuario, idRol, idEstadoUsuario),
                                "Rol y estado actualizados"));
        }

        @Operation(summary = "Buscar usuario por correo", description = "Busca un usuario en el sistema utilizando su dirección de correo electrónico exacta.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Usuario encontrado correctamente"),
                        @ApiResponse(responseCode = "404", description = "No existe ningún usuario asociado a ese correo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 404,
                                          "error": "No Encontrado",
                                          "message": "No encontrado T_T",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """))),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 500,
                                          "error": "Internal Server Error",
                                          "message": "Error interno del servidor",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """)))
        })
        @GetMapping("/buscar-por-correo")
        public ResponseEntity<ApiResponsev1<UsuarioDTO>> buscarPorCorreo(
                        @Parameter(description = "Correo electrónico del usuario a buscar", example = "juan.perez@email.com") @RequestParam String correo) {
                return ResponseEntity.ok(
                                ApiResponsev1.success(usuarioService.buscarPorCorreo(correo), "Usuario encontrado"));
        }

        @Operation(summary = "Listar usuarios por rol", description = "Retorna todos los usuarios que están asociados actualmente a un rol específico.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Usuarios obtenidos correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 200,
                                          "error": "OK",
                                          "message": "Usuarios por rol encontrados",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """))),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 500,
                                          "error": "Internal Server Error",
                                          "message": "Error interno del servidor",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """)))
        })
        @GetMapping("/rol/{idRol}")
        public ResponseEntity<ApiResponsev1<List<UsuarioDTO>>> listarPorRol(
                        @Parameter(description = "Identificador del rol a buscar", example = "1") @PathVariable Long idRol) {
                return ResponseEntity.ok(ApiResponsev1.success(usuarioService.listarPorRol(idRol),
                                "Usuarios por rol encontrados"));
        }

        @Operation(summary = "Listar usuarios por estado", description = "Retorna todos los usuarios que se encuentran en un estado específico (ej. Activo, Bloqueado).")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Usuarios obtenidos correctamente"),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @GetMapping("/estado/{idEstadoUsuario}")
        public ResponseEntity<ApiResponsev1<List<UsuarioDTO>>> listarPorEstado(
                        @Parameter(description = "Identificador del estado del usuario", example = "1") @PathVariable Long idEstadoUsuario) {
                return ResponseEntity.ok(ApiResponsev1.success(usuarioService.listarPorEstado(idEstadoUsuario),
                                "Usuarios por estado encontrados"));
        }

        @Operation(summary = "Listar usuarios por nivel de cuenta", description = "Retorna todos los usuarios que poseen un nivel de cuenta igual al especificado.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Usuarios obtenidos correctamente"),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @GetMapping("/nivel/{nivelCuenta}")
        public ResponseEntity<ApiResponsev1<List<UsuarioDTO>>> listarPorNivel(
                        @Parameter(description = "Nivel de cuenta a buscar", example = "10") @PathVariable Integer nivelCuenta) {
                return ResponseEntity.ok(ApiResponsev1.success(usuarioService.listarPorNivel(nivelCuenta),
                                "Usuarios por nivel encontrados"));
        }

        @Operation(summary = "Listar usuarios registrados después de una fecha", description = "Retorna una lista de usuarios cuya fecha de registro sea estrictamente posterior a la fecha indicada.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Usuarios encontrados correctamente"),
                        @ApiResponse(responseCode = "400", description = "Formato de fecha inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 400,
                                          "error": "Bad Request",
                                          "message": "Formato de fecha inválido",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """))),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 500,
                                          "error": "Internal Server Error",
                                          "message": "Error interno del servidor",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """)))
        })
        @GetMapping("/registrados-despues")
        public ResponseEntity<ApiResponsev1<List<UsuarioDTO>>> listarRegistradosDespuesDe(
                        @Parameter(description = "Fecha inicial de búsqueda (Formato YYYY-MM-DD)", example = "2026-06-01") @RequestParam LocalDate fecha) {
                return ResponseEntity.ok(ApiResponsev1.success(usuarioService.listarRegistradosDespuesDe(fecha),
                                "Usuarios registrados después de la fecha encontrados"));
        }

        @Operation(summary = "Buscar usuarios por fecha de registro exacta", description = "Retorna los usuarios que fueron registrados exactamente en el día indicado.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Usuarios encontrados correctamente"),
                        @ApiResponse(responseCode = "400", description = "Formato de fecha inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 400,
                                          "error": "Bad Request",
                                          "message": "Formato de fecha inválido",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """))),
                        @ApiResponse(responseCode = "404", description = "No hay usuarios registrados en la fecha indicada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 404,
                                          "error": "Not Found",
                                          "message": "No encontrado T_T",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """))),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 500,
                                          "error": "Internal Server Error",
                                          "message": "Error interno del servidor",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """)))
        })
        @GetMapping("/buscar-por-fecha")
        public ResponseEntity<ApiResponsev1<List<UsuarioDTO>>> listarPorFecha(
                        @Parameter(description = "Fecha exacta de registro a buscar (Formato YYYY-MM-DD)", example = "2026-06-01") @RequestParam LocalDate fecha) {
                List<UsuarioDTO> usuarios = usuarioService.listarPorFechaVerificandoResultados(fecha);
                return ResponseEntity.ok(ApiResponsev1.success(usuarios, "Usuarios encontrados por fecha"));
        }

        @Operation(summary = "Listar roles", description = "Retorna todos los roles de sistema disponibles (ej. ADMIN, JUGADOR).")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Roles listados correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 200,
                                          "error": "OK",
                                          "message": "Roles listados exitosamente",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """))),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 500,
                                          "error": "Internal Server Error",
                                          "message": "Error interno del servidor",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """)))
        })
        @GetMapping("/roles")
        public ResponseEntity<ApiResponsev1<List<Rol>>> listarRoles() {
                return ResponseEntity
                                .ok(ApiResponsev1.success(rolService.listarRoles(), "Roles listados exitosamente"));
        }

        @Operation(summary = "Buscar rol por ID", description = "Obtiene la información detallada de un rol específico mediante su identificador.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Rol encontrado correctamente"),
                        @ApiResponse(responseCode = "404", description = "Rol no encontrado en la base de datos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 404,
                                          "error": "Not Found",
                                          "message": "No encontrado T_T",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """))),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 500,
                                          "error": "Internal Server Error",
                                          "message": "Error interno del servidor",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """)))
        })
        @GetMapping("/roles/{idRol}")
        public ResponseEntity<ApiResponsev1<Rol>> buscarRolPorId(
                        @Parameter(description = "Identificador único del rol", example = "2") @PathVariable Long idRol) {
                return ResponseEntity.ok(ApiResponsev1.success(rolService.buscarPorId(idRol), "Rol encontrado"));
        }

        @Operation(summary = "Listar estados de usuario", description = "Retorna todos los estados posibles que puede tener un usuario (ej. ACTIVO, INACTIVO, BANEADO).")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Estados listados correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 200,
                                          "error": "OK",
                                          "message": "Estados listados exitosamente",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """))),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 500,
                                          "error": "Internal Server Error",
                                          "message": "Error interno del servidor",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """)))
        })
        @GetMapping("/estados")
        public ResponseEntity<ApiResponsev1<List<EstadoUsuario>>> listarEstados() {
                return ResponseEntity.ok(ApiResponsev1.success(estadoUsuarioService.listarEstados(),
                                "Estados listados exitosamente"));
        }

        @Operation(summary = "Buscar estado de usuario por ID", description = "Obtiene la información de un estado de usuario específico mediante su identificador.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Estado encontrado correctamente"),
                        @ApiResponse(responseCode = "404", description = "Estado no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 404,
                                          "error": "Not Found",
                                          "message": "No encontrado T_T",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """))),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                                        {
                                          "status": 500,
                                          "error": "Internal Server Error",
                                          "message": "Error interno del servidor",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """)))
        })
        @GetMapping("/estados/{idEstadoUsuario}")
        public ResponseEntity<ApiResponsev1<EstadoUsuario>> buscarEstadoPorId(
                        @Parameter(description = "Identificador del estado de usuario", example = "1") @PathVariable Long idEstadoUsuario) {
                return ResponseEntity.ok(ApiResponsev1.success(estadoUsuarioService.buscarPorId(idEstadoUsuario),
                                "Estado encontrado"));
        }
}
