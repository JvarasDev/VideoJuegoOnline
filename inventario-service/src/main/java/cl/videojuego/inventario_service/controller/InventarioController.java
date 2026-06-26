package cl.videojuego.inventario_service.controller;

import cl.videojuego.inventario_service.dto.InventarioDTO;
import cl.videojuego.inventario_service.dto.InventarioRegistroDTO;
import cl.videojuego.inventario_service.exception.ErrorResponse;
import cl.videojuego.inventario_service.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventarios")
@Tag(name = "Inventario", description = "Endpoints para la gestión de inventarios")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @Operation(summary = "Listar todos los inventarios")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de inventarios obtenida exitosamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = InventarioDTO.class)), examples = @ExampleObject(value = """
                    [
                      {
                        "idItemInventario": 1,
                        "idInventario": 1,
                        "idReferenciaItem": 1,
                        "nombreItem": "Espada Básica",
                        "nombreTipoItem": "ARMA",
                        "cantidad": 1,
                        "fechaObtencion": "2026-06-17",
                        "equipado": true
                      }
                    ]
                    """))),

            @ApiResponse(responseCode = "404", description = "No se encontraron inventarios", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                    {
                      "status": 404,
                      "error": "RECURSO NO ENCONTRADO",
                      "message": "No se encontraron inventarios",
                      "timestamp": "2026-06-24T03:00:00"
                    }
                    """))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                    {
                      "status": 500,
                      "error": "INTERNAL SERVER ERROR",
                      "message": "Error interno del servidor al procesar la solicitud",
                      "timestamp": "2026-06-24T03:00:00"
                    }
                    """)))
    })
    @GetMapping
    public ResponseEntity<List<InventarioDTO>> listarTodos() {
        return ResponseEntity.ok(inventarioService.listarTodos());
    }

    @Operation(summary = "Registrar un nuevo inventario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operación procesada exitosamente"),
            @ApiResponse(responseCode = "201", description = "Inventario creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InventarioDTO.class), examples = @ExampleObject(value = """
                    {
                      "idItemInventario": 13,
                      "idInventario": 1,
                      "idReferenciaItem": 5,
                      "nombreItem": "Arco Celestial",
                      "nombreTipoItem": "ARMA",
                      "cantidad": 3,
                      "fechaObtencion": "2026-06-26",
                      "equipado": false
                    }
                    """))),
            @ApiResponse(responseCode = "400", description = "Petición inválida (Bad Request)"),
            @ApiResponse(responseCode = "409", description = "Conflicto de negocio", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                    {
                      "status": 409,
                      "error": "CONFLICT",
                      "message": "Conflicto de negocio (ej. el inventario ya existe)",
                      "timestamp": "2026-06-26T03:00:00"
                    }
                    """))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                    {
                      "status": 500,
                      "error": "INTERNAL SERVER ERROR",
                      "message": "Error interno del servidor al procesar la solicitud",
                      "timestamp": "2026-06-24T03:00:00"
                    }
                    """)))
    })
    @PostMapping
    public ResponseEntity<InventarioDTO> registrar(
            @Valid @RequestBody InventarioRegistroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.registrar(dto));
    }

    @Operation(summary = "Listar inventarios por ID de personaje")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inventarios del personaje obtenidos exitosamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = InventarioDTO.class)))),
            @ApiResponse(responseCode = "404", description = "No se encontraron inventarios para el personaje con el ID proporcionado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                    {
                      "status": 404,
                      "error": "RECURSO NO ENCONTRADO",
                      "message": "No se encontraron inventarios para el personaje con ID: 1",
                      "timestamp": "2026-06-24T03:00:00"
                    }
                    """))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                    {
                      "status": 500,
                      "error": "INTERNAL SERVER ERROR",
                      "message": "Error interno del servidor al procesar la solicitud",
                      "timestamp": "2026-06-24T03:00:00"
                    }
                    """)))
    })
    @GetMapping("/personaje/{idPersonaje}")
    public ResponseEntity<List<InventarioDTO>> listarPorPersonaje(
            @PathVariable Long idPersonaje) {
        return ResponseEntity.ok(inventarioService.listarPorPersonaje(idPersonaje));
    }

    @Operation(summary = "Listar inventarios por estado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inventarios obtenidos exitosamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = InventarioDTO.class)))),
            @ApiResponse(responseCode = "404", description = "No se encontraron inventarios para el estado proporcionado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                    {
                      "status": 404,
                      "error": "RECURSO NO ENCONTRADO",
                      "message": "No se encontraron inventarios para el estado con ID: 2",
                      "timestamp": "2026-06-24T03:00:00"
                    }
                    """))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                    {
                      "status": 500,
                      "error": "INTERNAL SERVER ERROR",
                      "message": "Error interno del servidor al procesar la solicitud",
                      "timestamp": "2026-06-24T03:00:00"
                    }
                    """)))
    })
    @GetMapping("/estado/{idEstadoInventario}")
    public ResponseEntity<List<InventarioDTO>> listarPorEstado(
            @PathVariable Long idEstadoInventario) {
        return ResponseEntity.ok(inventarioService.listarPorEstado(idEstadoInventario));
    }

    @Operation(summary = "Buscar inventario por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inventario obtenido exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InventarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                    {
                      "status": 404,
                      "error": "RECURSO NO ENCONTRADO",
                      "message": "Inventario no encontrado",
                      "timestamp": "2026-06-24T03:00:00"
                    }
                    """))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                    {
                      "status": 500,
                      "error": "INTERNAL SERVER ERROR",
                      "message": "Error interno del servidor al procesar la solicitud",
                      "timestamp": "2026-06-24T03:00:00"
                    }
                    """)))
    })
    @GetMapping("/{idInventario}")
    public ResponseEntity<InventarioDTO> buscarPorId(
            @PathVariable Long idInventario) {
        return ResponseEntity.ok(inventarioService.buscarPorId(idInventario));
    }
}