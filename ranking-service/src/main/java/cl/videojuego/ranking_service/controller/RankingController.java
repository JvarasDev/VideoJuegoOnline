package cl.videojuego.ranking_service.controller;

import cl.videojuego.ranking_service.dto.RankingDTO;
import cl.videojuego.ranking_service.dto.RankingRegistroDTO;
import cl.videojuego.ranking_service.service.RankingService;
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
@RequestMapping("/api/rankings")
@RequiredArgsConstructor
@Tag(
        name = "Rankings",
        description = "API para la gestión de rankings competitivos del videojuego"
)
public class RankingController {

    private final RankingService rankingService;

    @Operation(
            summary = "Listar todos los rankings",
            description = "Retorna una lista completa con todos los rankings registrados en el sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Rankings listados correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = RankingDTO.class)
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
    public ResponseEntity<List<RankingDTO>> listarTodos() {
        return ResponseEntity.ok(rankingService.listarTodos());
    }

    @Operation(
            summary = "Registrar ranking",
            description = "Permite registrar una nueva posición de ranking asociada a un personaje, una liga y una temporada."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Ranking registrado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RankingDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos enviados en la solicitud",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Personaje, liga o temporada no encontrada",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<RankingDTO> registrar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos necesarios para registrar una nueva posición de ranking",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RankingRegistroDTO.class)
                    )
            )
            @Valid @RequestBody RankingRegistroDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(rankingService.registrar(dto));
    }

    @Operation(
            summary = "Listar rankings por personaje",
            description = "Retorna todos los rankings asociados a un personaje específico. El personaje se valida mediante comunicación con personaje-service."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Rankings del personaje listados correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = RankingDTO.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Personaje no encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping("/personaje/{idPersonaje}")
    public ResponseEntity<List<RankingDTO>> listarPorPersonaje(
            @Parameter(
                    description = "Identificador único del personaje",
                    example = "1"
            )
            @PathVariable Long idPersonaje
    ) {
        return ResponseEntity.ok(rankingService.listarPorPersonaje(idPersonaje));
    }

    @Operation(
            summary = "Listar rankings por liga",
            description = "Retorna todos los rankings asociados a una liga específica."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Rankings por liga listados correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = RankingDTO.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping("/liga/{idLiga}")
    public ResponseEntity<List<RankingDTO>> listarPorLiga(
            @Parameter(
                    description = "Identificador de la liga",
                    example = "1"
            )
            @PathVariable Long idLiga
    ) {
        return ResponseEntity.ok(rankingService.listarPorLiga(idLiga));
    }

    @Operation(
            summary = "Listar rankings por temporada",
            description = "Retorna todos los rankings asociados a una temporada específica."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Rankings por temporada listados correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = RankingDTO.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping("/temporada/{idTemporada}")
    public ResponseEntity<List<RankingDTO>> listarPorTemporada(
            @Parameter(
                    description = "Identificador de la temporada",
                    example = "1"
            )
            @PathVariable Long idTemporada
    ) {
        return ResponseEntity.ok(rankingService.listarPorTemporada(idTemporada));
    }

    @Operation(
            summary = "Filtrar rankings por puntos mínimos",
            description = "Retorna todos los rankings cuyos puntos sean mayores o iguales al valor indicado."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Rankings filtrados correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = RankingDTO.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Valor de puntos inválido",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping("/puntos-minimos")
    public ResponseEntity<List<RankingDTO>> listarPorPuntosMinimos(
            @Parameter(
                    description = "Cantidad mínima de puntos requerida",
                    example = "1000"
            )
            @RequestParam Integer puntos
    ) {
        return ResponseEntity.ok(rankingService.listarPorPuntosMinimos(puntos));
    }

    @Operation(
            summary = "Obtener Top 10 del ranking",
            description = "Retorna los 10 personajes mejor posicionados en el ranking competitivo."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Top 10 obtenido correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = RankingDTO.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping("/top10")
    public ResponseEntity<List<RankingDTO>> listarTop10() {
        return ResponseEntity.ok(rankingService.listarTop10());
    }

    @Operation(
            summary = "Buscar ranking por ID",
            description = "Obtiene la información detallada de un ranking específico mediante su identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ranking encontrado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RankingDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ranking no encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping("/{idRanking}")
    public ResponseEntity<RankingDTO> buscarPorId(
            @Parameter(
                    description = "Identificador único del ranking",
                    example = "1"
            )
            @PathVariable Long idRanking
    ) {
        return ResponseEntity.ok(rankingService.buscarPorId(idRanking));
    }
}