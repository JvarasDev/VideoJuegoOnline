package cl.videojuego.combate_service.controller;

import cl.videojuego.combate_service.dto.CombateDTO;
import cl.videojuego.combate_service.dto.CombateRegistroDTO;
import cl.videojuego.combate_service.service.CombateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/combates")
@RequiredArgsConstructor
public class CombateController {

    private final CombateService combateService;


    @GetMapping
    public ResponseEntity<List<CombateDTO>> listarTodos() {
        return ResponseEntity.ok(combateService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<CombateDTO> registrar(
            @Valid @RequestBody CombateRegistroDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(combateService.registrar(dto));
    }

    @GetMapping("/atacante/{idPersonaje}")
    public ResponseEntity<List<CombateDTO>> listarPorAtacante(@PathVariable Long idPersonaje) {
        return ResponseEntity.ok(combateService.listarPorAtacante(idPersonaje));
    }

    @GetMapping("/defensor/{idPersonaje}")
    public ResponseEntity<List<CombateDTO>> listarPorDefensor(@PathVariable Long idPersonaje) {
        return ResponseEntity.ok(combateService.listarPorDefensor(idPersonaje));
    }

    @GetMapping("/ganador/{idGanador}")
    public ResponseEntity<List<CombateDTO>> listarPorGanador(@PathVariable Long idGanador) {
        return ResponseEntity.ok(combateService.listarPorGanador(idGanador));
    }

    @GetMapping("/tipo/{idTipoCombate}")
    public ResponseEntity<List<CombateDTO>> listarPorTipo(@PathVariable Long idTipoCombate) {
        return ResponseEntity.ok(combateService.listarPorTipo(idTipoCombate));
    }

    @GetMapping("/estado/{idEstadoCombate}")
    public ResponseEntity<List<CombateDTO>> listarPorEstado(@PathVariable Long idEstadoCombate) {
        return ResponseEntity.ok(combateService.listarPorEstado(idEstadoCombate));
    }
}