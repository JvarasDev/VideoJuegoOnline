package cl.videojuego.inventario_service.controller;

import cl.videojuego.inventario_service.dto.ItemInventarioDTO;
import cl.videojuego.inventario_service.dto.ItemInventarioRegistroDTO;
import cl.videojuego.inventario_service.exception.ErrorResponse;
import cl.videojuego.inventario_service.service.ItemInventarioService;
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
@RequestMapping("/api/items-inventario")
@Tag(name = "Items Inventario", description = "Endpoints para la gestión de ítems en el inventario")
public class ItemInventarioController {

    private final ItemInventarioService itemInventarioService;

    public ItemInventarioController(
            ItemInventarioService itemInventarioService
    ) {
        this.itemInventarioService = itemInventarioService;
    }

    @Operation(summary = "Listar todos los ítems de inventario")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de ítems obtenida exitosamente",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ItemInventarioDTO.class)))
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
                      "error": "INTERNAL SERVER ERROR",
                      "message": "Error interno del servidor al procesar la solicitud",
                      "timestamp": "2026-06-24T03:00:00"
                    }
                    """
                )
            )
        )
    })
    @GetMapping
    public ResponseEntity<List<ItemInventarioDTO>> listarTodos() {
        return ResponseEntity.ok(itemInventarioService.listarTodos());
    }

    @Operation(summary = "Registrar un nuevo ítem en el inventario")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Ítem creado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemInventarioDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Petición inválida por errores de validación"
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
                      "error": "INTERNAL SERVER ERROR",
                      "message": "Error interno del servidor al procesar la solicitud",
                      "timestamp": "2026-06-24T03:00:00"
                    }
                    """
                )
            )
        )
    })
    @PostMapping
    public ResponseEntity<ItemInventarioDTO> registrar(
            @Valid @RequestBody ItemInventarioRegistroDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemInventarioService.registrar(dto));
    }

    @Operation(summary = "Listar ítems por inventario")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Ítems del inventario obtenidos exitosamente",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ItemInventarioDTO.class)))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron items para el inventario proporcionado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                      "status": 404,
                      "error": "RECURSO NO ENCONTRADO",
                      "message": "No se encontraron items para el inventario con ID: 1",
                      "timestamp": "2026-06-24T03:00:00"
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
                      "error": "INTERNAL SERVER ERROR",
                      "message": "Error interno del servidor al procesar la solicitud",
                      "timestamp": "2026-06-24T03:00:00"
                    }
                    """
                )
            )
        )
    })
    @GetMapping("/inventario/{idInventario}")
    public ResponseEntity<List<ItemInventarioDTO>> listarPorInventario(
            @PathVariable Long idInventario
    ) {
        return ResponseEntity.ok(itemInventarioService.listarPorInventario(idInventario));
    }

    @Operation(summary = "Listar ítems por referencia")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Ítems por referencia obtenidos exitosamente",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ItemInventarioDTO.class)))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron items para la referencia proporcionada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                      "status": 404,
                      "error": "RECURSO NO ENCONTRADO",
                      "message": "No se encontraron items para la referencia con ID: 5",
                      "timestamp": "2026-06-24T03:00:00"
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
                      "error": "INTERNAL SERVER ERROR",
                      "message": "Error interno del servidor al procesar la solicitud",
                      "timestamp": "2026-06-24T03:00:00"
                    }
                    """
                )
            )
        )
    })
    @GetMapping("/referencia/{idReferencia}")
    public ResponseEntity<List<ItemInventarioDTO>> listarPorReferencia(
            @PathVariable Long idReferencia
    ) {
        return ResponseEntity.ok(itemInventarioService.listarPorReferencia(idReferencia));
    }

    @Operation(summary = "Listar ítems equipados o no equipados")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Ítems obtenidos exitosamente",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ItemInventarioDTO.class)))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron items con el estado de equipamiento especificado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                      "status": 404,
                      "error": "RECURSO NO ENCONTRADO",
                      "message": "No se encontraron items con estado equipado: true",
                      "timestamp": "2026-06-24T03:00:00"
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
                      "error": "INTERNAL SERVER ERROR",
                      "message": "Error interno del servidor al procesar la solicitud",
                      "timestamp": "2026-06-24T03:00:00"
                    }
                    """
                )
            )
        )
    })
    @GetMapping("/equipados")
    public ResponseEntity<List<ItemInventarioDTO>> listarEquipados(
            @RequestParam Boolean equipado
    ) {
        return ResponseEntity.ok(itemInventarioService.listarEquipados(equipado));
    }
}