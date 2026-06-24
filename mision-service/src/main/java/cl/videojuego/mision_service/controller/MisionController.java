package cl.videojuego.mision_service.controller;

import cl.videojuego.mision_service.dto.MisionDTO;
import cl.videojuego.mision_service.dto.MisionRegistroDTO;
import cl.videojuego.mision_service.service.MisionService;
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
                    content = @Content
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
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tipo de misión o estado no encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
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
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
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
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
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
                    content = @Content
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
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
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
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
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