package cl.videojuego.mision_service.controller;

import cl.videojuego.mision_service.dto.MisionDTO;
import cl.videojuego.mision_service.dto.MisionRegistroDTO;
import cl.videojuego.mision_service.service.MisionService;
import cl.videojuego.mision_service.exception.ErrorResponse;
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
@RequestMapping("/api/misiones")
@RequiredArgsConstructor
@Tag(
        name = "Misiones",
        description = "API para la gestión de misiones del videojuego"
)
public class MisionController {

    private final MisionService misionService;

    @Operation(
            summary = "Listar todas las misiones",
            description = "Retorna una lista completa con todas las misiones registradas en el sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Misiones listadas correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = MisionDTO.class)
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
                                      "message": "Error al listar las misiones",
                                      "timestamp": "2026-06-24T02:26:31.0983806"
                                    }
                                    """
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<MisionDTO>> listarTodas() {
        return ResponseEntity.ok(misionService.listarTodas());
    }

    @Operation(
            summary = "Registrar nueva misión",
            description = "Permite registrar una nueva misión indicando nombre, descripción, recompensa, nivel mínimo, tipo y estado."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Misión registrada correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MisionDTO.class)
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
                                      "message": "El nombre de la misión es obligatorio",
                                      "timestamp": "2026-06-24T02:26:31.0983806"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tipo de misión o estado no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "status": 404,
                                      "error": "Not Found",
                                      "message": "Tipo de misión no encontrado",
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
                                      "message": "Error al registrar la misión",
                                      "timestamp": "2026-06-24T02:26:31.0983806"
                                    }
                                    """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<MisionDTO> registrar(
            @Valid @RequestBody MisionRegistroDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(misionService.registrar(dto));
    }

    @Operation(
            summary = "Listar misiones por tipo",
            description = "Retorna las misiones asociadas a un tipo específico."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Misiones encontradas correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = MisionDTO.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tipo de misión no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "status": 404,
                                      "error": "Not Found",
                                      "message": "Tipo de misión no encontrado",
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
                                      "message": "Error al listar misiones por tipo",
                                      "timestamp": "2026-06-24T02:26:31.0983806"
                                    }
                                    """
                            )
                    )
            )
    })
    @GetMapping("/tipo/{idTipoMision}")
    public ResponseEntity<List<MisionDTO>> listarPorTipo(
            @Parameter(
                    description = "Identificador del tipo de misión",
                    example = "1"
            )
            @PathVariable Long idTipoMision
    ) {
        return ResponseEntity.ok(misionService.listarPorTipo(idTipoMision));
    }

    @Operation(
            summary = "Listar misiones por estado",
            description = "Retorna las misiones asociadas a un estado específico."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Misiones encontradas correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = MisionDTO.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Estado de misión no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "status": 404,
                                      "error": "Not Found",
                                      "message": "Estado de misión no encontrado",
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
                                      "message": "Error al listar misiones por estado",
                                      "timestamp": "2026-06-24T02:26:31.0983806"
                                    }
                                    """
                            )
                    )
            )
    })
    @GetMapping("/estado/{idEstadoMision}")
    public ResponseEntity<List<MisionDTO>> listarPorEstado(
            @Parameter(
                    description = "Identificador del estado de la misión",
                    example = "1"
            )
            @PathVariable Long idEstadoMision
    ) {
        return ResponseEntity.ok(misionService.listarPorEstado(idEstadoMision));
    }

    @Operation(
            summary = "Listar misiones por nivel mínimo",
            description = "Retorna las misiones asociadas a un nivel mínimo específico."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Misiones encontradas correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = MisionDTO.class)
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
                                      "message": "Error al listar misiones por nivel",
                                      "timestamp": "2026-06-24T02:26:31.0983806"
                                    }
                                    """
                            )
                    )
            )
    })
    @GetMapping("/nivel/{nivelMinimo}")
    public ResponseEntity<List<MisionDTO>> listarPorNivel(
            @Parameter(
                    description = "Nivel mínimo requerido para la misión",
                    example = "10"
            )
            @PathVariable Integer nivelMinimo
    ) {
        return ResponseEntity.ok(misionService.listarPorNivel(nivelMinimo));
    }

    @Operation(
            summary = "Buscar misiones por nombre",
            description = "Permite buscar misiones utilizando una parte del nombre."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Misiones encontradas correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = MisionDTO.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Misión no encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "status": 404,
                                      "error": "Not Found",
                                      "message": "No se encontraron misiones con ese nombre",
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
                                      "message": "Error al buscar misiones",
                                      "timestamp": "2026-06-24T02:26:31.0983806"
                                    }
                                    """
                            )
                    )
            )
    })
    @GetMapping("/buscar-por-nombre")
    public ResponseEntity<List<MisionDTO>> buscarPorNombre(
            @Parameter(
                    description = "Nombre o parte del nombre de la misión",
                    example = "Dragón"
            )
            @RequestParam String nombre
    ) {
        return ResponseEntity.ok(misionService.buscarPorNombre(nombre));
    }

    @Operation(
            summary = "Buscar misiones por recompensa máxima",
            description = "Retorna las misiones cuya recompensa sea menor o igual al valor indicado."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Misiones encontradas correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = MisionDTO.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Valor de recompensa inválido",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "status": 400,
                                      "error": "Bad Request",
                                      "message": "Valor de recompensa inválido",
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
                                      "message": "Error al buscar misiones por recompensa",
                                      "timestamp": "2026-06-24T02:26:31.0983806"
                                    }
                                    """
                            )
                    )
            )
    })
    @GetMapping("/recompensa-menor")
    public ResponseEntity<List<MisionDTO>> buscarPorRecompensa(
            @Parameter(
                    description = "Recompensa máxima permitida",
                    example = "5000"
            )
            @RequestParam Integer recompensa
    ) {
        return ResponseEntity.ok(misionService.buscarPorRecompensa(recompensa));
    }
}