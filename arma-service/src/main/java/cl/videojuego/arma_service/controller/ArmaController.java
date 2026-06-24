package cl.videojuego.arma_service.controller;

import cl.videojuego.arma_service.dto.ArmaDTO;
import cl.videojuego.arma_service.dto.ArmaRegistroDTO;
import cl.videojuego.arma_service.service.ArmaService;
import cl.videojuego.arma_service.exception.ErrorResponse;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/armas")
@RequiredArgsConstructor
@Tag(
        name = "Armas",
        description = "API para la gestión de armas del videojuego"
)
public class ArmaController {

        private final ArmaService armaService;

        @Operation(
                summary = "Listar todas las armas",
                description = "Retorna una lista completa con todas las armas registradas en el sistema."
        )
        @ApiResponses({
                @ApiResponse(
                        responseCode = "200",
                        description = "Armas listadas correctamente",
                        content = @Content(
                                mediaType = "application/json",
                                array = @ArraySchema(
                                        schema = @Schema(implementation = ArmaDTO.class)
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class),
                                examples = @ExampleObject(
                                        value = """
                                        {
                                          "status": 500,
                                          "error": "Internal Server Error",
                                          "message": "Error interno del servidor al obtener las armas",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """
                                )
                        )
                )
        })
        @GetMapping
        public ResponseEntity<List<ArmaDTO>> listarTodas() {
                return ResponseEntity.ok(armaService.listarTodas());
        }

        @Operation(
                summary = "Registrar nueva arma",
                description = "Permite registrar una nueva arma indicando nombre, daño, nivel mínimo, precio, tipo de arma y rareza."
        )
        @ApiResponses({
                @ApiResponse(
                        responseCode = "201",
                        description = "Arma registrada correctamente",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ArmaDTO.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Datos inválidos enviados en la solicitud",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class),
                                examples = @ExampleObject(
                                        value = """
                                        {
                                          "status": 400,
                                          "error": "Bad Request",
                                          "message": "El nombre del arma es obligatorio",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Tipo de arma o rareza no encontrada",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class),
                                examples = @ExampleObject(
                                        value = """
                                        {
                                          "status": 404,
                                          "error": "Not Found",
                                          "message": "Tipo de arma no encontrado",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class),
                                examples = @ExampleObject(
                                        value = """
                                        {
                                          "status": 500,
                                          "error": "Internal Server Error",
                                          "message": "Error interno al registrar el arma",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """
                                )
                        )
                )
        })
        @PostMapping
        public ResponseEntity<ArmaDTO> registrar(
                @Valid @RequestBody ArmaRegistroDTO dto
        ) {
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(armaService.registrar(dto));
        }

        @Operation(
                summary = "Listar armas por tipo",
                description = "Retorna las armas asociadas a un tipo específico, por ejemplo espada, arco o bastón."
        )
        @ApiResponses({
                @ApiResponse(
                        responseCode = "200",
                        description = "Armas por tipo listadas correctamente",
                        content = @Content(
                                mediaType = "application/json",
                                array = @ArraySchema(
                                        schema = @Schema(implementation = ArmaDTO.class)
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Tipo de arma no encontrado",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class),
                                examples = @ExampleObject(
                                        value = """
                                        {
                                          "status": 404,
                                          "error": "Not Found",
                                          "message": "Tipo de arma no encontrado",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class),
                                examples = @ExampleObject(
                                        value = """
                                        {
                                          "status": 500,
                                          "error": "Internal Server Error",
                                          "message": "Error interno al listar armas por tipo",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """
                                )
                        )
                )
        })
        @GetMapping("/tipo/{idTipoArma}")
        public ResponseEntity<List<ArmaDTO>> listarPorTipo(
                @Parameter(
                        description = "Identificador del tipo de arma",
                        example = "1"
                )
                @PathVariable Long idTipoArma
        ) {
                return ResponseEntity.ok(armaService.listarPorTipo(idTipoArma));
        }

        @Operation(
                summary = "Listar armas por rareza",
                description = "Retorna las armas asociadas a una rareza específica, por ejemplo común, rara, épica o legendaria."
        )
        @ApiResponses({
                @ApiResponse(
                        responseCode = "200",
                        description = "Armas por rareza listadas correctamente",
                        content = @Content(
                                mediaType = "application/json",
                                array = @ArraySchema(
                                        schema = @Schema(implementation = ArmaDTO.class)
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Rareza de arma no encontrada",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class),
                                examples = @ExampleObject(
                                        value = """
                                        {
                                          "status": 404,
                                          "error": "Not Found",
                                          "message": "Rareza de arma no encontrada",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class),
                                examples = @ExampleObject(
                                        value = """
                                        {
                                          "status": 500,
                                          "error": "Internal Server Error",
                                          "message": "Error interno al listar armas por rareza",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """
                                )
                        )
                )
        })
        @GetMapping("/rareza/{idRarezaArma}")
        public ResponseEntity<List<ArmaDTO>> listarPorRareza(
                @Parameter(
                        description = "Identificador de la rareza del arma",
                        example = "2"
                )
                @PathVariable Long idRarezaArma
        ) {
                return ResponseEntity.ok(armaService.listarPorRareza(idRarezaArma));
        }

        @Operation(
                summary = "Listar armas por nivel mínimo",
                description = "Retorna las armas que requieren un nivel mínimo específico para poder ser utilizadas."
        )
        @ApiResponses({
                @ApiResponse(
                        responseCode = "200",
                        description = "Armas por nivel mínimo listadas correctamente",
                        content = @Content(
                                mediaType = "application/json",
                                array = @ArraySchema(
                                        schema = @Schema(implementation = ArmaDTO.class)
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class),
                                examples = @ExampleObject(
                                        value = """
                                        {
                                          "status": 500,
                                          "error": "Internal Server Error",
                                          "message": "Error interno al listar armas por nivel",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """
                                )
                        )
                )
        })
        @GetMapping("/nivel/{nivelMinimo}")
        public ResponseEntity<List<ArmaDTO>> listarPorNivel(
                @Parameter(
                        description = "Nivel mínimo requerido para utilizar el arma",
                        example = "10"
                )
                @PathVariable Integer nivelMinimo
        ) {
                return ResponseEntity.ok(armaService.listarPorNivel(nivelMinimo));
        }

        @Operation(
                summary = "Buscar armas por nombre",
                description = "Permite buscar armas utilizando una parte del nombre, sin distinguir mayúsculas o minúsculas."
        )
        @ApiResponses({
                @ApiResponse(
                        responseCode = "200",
                        description = "Armas encontradas correctamente",
                        content = @Content(
                                mediaType = "application/json",
                                array = @ArraySchema(
                                        schema = @Schema(implementation = ArmaDTO.class)
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Arma no encontrada",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class),
                                examples = @ExampleObject(
                                        value = """
                                        {
                                          "status": 404,
                                          "error": "Not Found",
                                          "message": "No se encontraron armas con ese nombre",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class),
                                examples = @ExampleObject(
                                        value = """
                                        {
                                          "status": 500,
                                          "error": "Internal Server Error",
                                          "message": "Error interno al buscar armas",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """
                                )
                        )
                )
        })
        @GetMapping("/buscar-por-nombre")
        public ResponseEntity<List<ArmaDTO>> buscarPorNombre(
                @Parameter(
                        description = "Nombre o parte del nombre del arma",
                        example = "Espada"
                )
                @RequestParam String nombre
        ) {
                return ResponseEntity.ok(armaService.buscarPorNombre(nombre));
        }

        @Operation(
                summary = "Buscar armas por precio máximo",
                description = "Retorna las armas cuyo precio sea menor o igual al valor indicado."
        )
        @ApiResponses({
                @ApiResponse(
                        responseCode = "200",
                        description = "Armas filtradas por precio obtenidas correctamente",
                        content = @Content(
                                mediaType = "application/json",
                                array = @ArraySchema(
                                        schema = @Schema(implementation = ArmaDTO.class)
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Precio inválido",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class),
                                examples = @ExampleObject(
                                        value = """
                                        {
                                          "status": 400,
                                          "error": "Bad Request",
                                          "message": "Precio inválido",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class),
                                examples = @ExampleObject(
                                        value = """
                                        {
                                          "status": 500,
                                          "error": "Internal Server Error",
                                          "message": "Error interno del servidor",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """
                                )
                        )
                )
        })
        @GetMapping("/precio-menor")
        public ResponseEntity<List<ArmaDTO>> buscarPorPrecio(
                @Parameter(
                        description = "Precio máximo permitido para la búsqueda",
                        example = "10000"
                )
                @RequestParam Integer precio
        ) {
                return ResponseEntity.ok(armaService.buscarPorPrecio(precio));
        }

        @Operation(
                summary = "Buscar arma por ID",
                description = "Permite obtener la información detallada de un arma específica mediante su identificador."
        )
        @ApiResponses({
                @ApiResponse(
                        responseCode = "200",
                        description = "Arma encontrada correctamente",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ArmaDTO.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Arma no encontrada",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class),
                                examples = @ExampleObject(
                                        value = """
                                        {
                                          "status": 404,
                                          "error": "Not Found",
                                          "message": "Arma con ID 1 no encontrada",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Error interno del servidor",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class),
                                examples = @ExampleObject(
                                        value = """
                                        {
                                          "status": 500,
                                          "error": "Internal Server Error",
                                          "message": "Error interno del servidor",
                                          "timestamp": "2026-06-24T02:26:31.0983806"
                                        }
                                        """
                                )
                        )
                )
        })
        @GetMapping("/{idArma}")
        public ResponseEntity<ArmaDTO> buscarPorId(
                @Parameter(
                        description = "Identificador único del arma",
                        example = "1"
                )
                @PathVariable Long idArma
        ) {
                return ResponseEntity.ok(armaService.buscarPorId(idArma));
        }
}