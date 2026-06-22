package cl.videojuego.combate_service.controller;

import cl.videojuego.combate_service.dto.CombateDTO;
import cl.videojuego.combate_service.dto.CombateRegistroDTO;
import cl.videojuego.combate_service.service.CombateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/combates")
@RequiredArgsConstructor
@Tag(name = "Combates", description = "Operaciones relacionadas con el sistema de combates entre personajes")
public class CombateController {

    private final CombateService combateService;

    @Operation(summary = "Listar todos los combates", description = "Retorna una lista completa de todos los combates registrados en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Combates listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<CombateDTO>> listarTodos() {
        return ResponseEntity.ok(combateService.listarTodos());
    }

    @Operation(summary = "Registrar nuevo combate", description = "Permite registrar un nuevo combate en el sistema indicando el atacante, defensor, ganador y demás detalles.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Combate registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
            @ApiResponse(responseCode = "404", description = "Personaje, tipo de combate o estado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<CombateDTO> registrar(
            @Valid @RequestBody CombateRegistroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(combateService.registrar(dto));
    }

    @Operation(summary = "Listar combates por atacante", description = "Retorna todos los combates donde el personaje especificado actuó como atacante.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Combates listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/atacante/{idPersonaje}")
    public ResponseEntity<List<CombateDTO>> listarPorAtacante(
            @Parameter(description = "ID del personaje atacante", example = "1") @PathVariable Long idPersonaje) {
        return ResponseEntity.ok(combateService.listarPorAtacante(idPersonaje));
    }

    @Operation(summary = "Listar combates por defensor", description = "Retorna todos los combates donde el personaje especificado actuó como defensor.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Combates listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/defensor/{idPersonaje}")
    public ResponseEntity<List<CombateDTO>> listarPorDefensor(
            @Parameter(description = "ID del personaje defensor", example = "2") @PathVariable Long idPersonaje) {
        return ResponseEntity.ok(combateService.listarPorDefensor(idPersonaje));
    }

    @Operation(summary = "Listar combates por ganador", description = "Retorna todos los combates que fueron ganados por el personaje especificado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Combates listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/ganador/{idGanador}")
    public ResponseEntity<List<CombateDTO>> listarPorGanador(
            @Parameter(description = "ID del personaje ganador", example = "1") @PathVariable Long idGanador) {
        return ResponseEntity.ok(combateService.listarPorGanador(idGanador));
    }

    @Operation(summary = "Listar combates por tipo", description = "Retorna todos los combates de un tipo específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Combates listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/tipo/{idTipoCombate}")
    public ResponseEntity<List<CombateDTO>> listarPorTipo(
            @Parameter(description = "ID del tipo de combate", example = "1") @PathVariable Long idTipoCombate) {
        return ResponseEntity.ok(combateService.listarPorTipo(idTipoCombate));
    }

    @Operation(summary = "Listar combates por estado", description = "Retorna todos los combates que se encuentran en un estado específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Combates listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/estado/{idEstadoCombate}")
    public ResponseEntity<List<CombateDTO>> listarPorEstado(
            @Parameter(description = "ID del estado de combate", example = "2") @PathVariable Long idEstadoCombate) {
        return ResponseEntity.ok(combateService.listarPorEstado(idEstadoCombate));
    }
}