package cl.videojuego.pago_service.controller;

import cl.videojuego.pago_service.dto.PagoDTO;
import cl.videojuego.pago_service.dto.PagoRegistroDTO;
import cl.videojuego.pago_service.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
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
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
@Tag(
        name = "Pagos",
        description = "API para la gestión de pagos de la tienda del videojuego"
)
public class PagoController {

    private final PagoService pagoService;

    @Operation(
            summary = "Listar todos los pagos",
            description = "Retorna una lista completa con todos los pagos registrados en el sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de pagos obtenida correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = PagoDTO.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<List<PagoDTO>> listarTodos() {
        return ResponseEntity.ok(pagoService.listarTodos());
    }

    @Operation(
            summary = "Registrar un nuevo pago",
            description = "Crea un nuevo registro de pago asociado a un usuario y a un producto de la tienda."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Pago registrado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PagoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos enviados en la solicitud",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario, producto, método de pago o estado no encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Stock insuficiente para realizar la compra",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<PagoDTO> registrar(
            @Valid @RequestBody PagoRegistroDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pagoService.registrar(dto));
    }

    @Operation(
            summary = "Buscar pago por ID",
            description = "Permite obtener la información detallada de un pago específico mediante su identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Pago encontrado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PagoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pago no encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping("/{idPago}")
    public ResponseEntity<PagoDTO> buscarPorId(
            @Parameter(
                    description = "Identificador único del pago",
                    example = "1"
            )
            @PathVariable Long idPago
    ) {
        return ResponseEntity.ok(pagoService.buscarPorId(idPago));
    }

    @Operation(
            summary = "Listar pagos por usuario",
            description = "Retorna todos los pagos asociados a un usuario específico."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Pagos del usuario obtenidos correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = PagoDTO.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PagoDTO>> listarPorUsuario(
            @Parameter(
                    description = "Identificador del usuario propietario del pago",
                    example = "1"
            )
            @PathVariable Long idUsuario
    ) {
        return ResponseEntity.ok(pagoService.listarPorUsuario(idUsuario));
    }

    @Operation(
            summary = "Listar pagos por estado",
            description = "Retorna todos los pagos filtrados por su estado."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Pagos por estado obtenidos correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = PagoDTO.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping("/estado/{idEstadoPago}")
    public ResponseEntity<List<PagoDTO>> listarPorEstado(
            @Parameter(
                    description = "Identificador del estado del pago",
                    example = "1"
            )
            @PathVariable Long idEstadoPago
    ) {
        return ResponseEntity.ok(pagoService.listarPorEstado(idEstadoPago));
    }
}
