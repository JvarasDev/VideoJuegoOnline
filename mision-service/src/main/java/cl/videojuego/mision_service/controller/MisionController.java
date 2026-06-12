package cl.videojuego.mision_service.controller;

import cl.videojuego.mision_service.dto.MisionDTO;
import cl.videojuego.mision_service.dto.MisionRegistroDTO;
import cl.videojuego.mision_service.service.MisionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/misiones")
@RequiredArgsConstructor
public class MisionController {

    private final MisionService misionService;

    @GetMapping
    public ResponseEntity<List<MisionDTO>> listarTodas() {
        return ResponseEntity.ok(misionService.listarTodas());
    }

    @PostMapping
    public ResponseEntity<MisionDTO> registrar(
            @Valid @RequestBody MisionRegistroDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(misionService.registrar(dto));
    }

    @GetMapping("/tipo/{idTipoMision}")
    public ResponseEntity<List<MisionDTO>> listarPorTipo(
            @PathVariable Long idTipoMision
    ) {
        return ResponseEntity.ok(misionService.listarPorTipo(idTipoMision));
    }

    @GetMapping("/estado/{idEstadoMision}")
    public ResponseEntity<List<MisionDTO>> listarPorEstado(
            @PathVariable Long idEstadoMision
    ) {
        return ResponseEntity.ok(misionService.listarPorEstado(idEstadoMision));
    }

    @GetMapping("/nivel/{nivelMinimo}")
    public ResponseEntity<List<MisionDTO>> listarPorNivel(
            @PathVariable Integer nivelMinimo
    ) {
        return ResponseEntity.ok(misionService.listarPorNivel(nivelMinimo));
    }

    @GetMapping("/buscar-por-nombre")
    public ResponseEntity<List<MisionDTO>> buscarPorNombre(
            @RequestParam String nombre
    ) {
        return ResponseEntity.ok(misionService.buscarPorNombre(nombre));
    }

    @GetMapping("/recompensa-menor")
    public ResponseEntity<List<MisionDTO>> buscarPorRecompensa(
            @RequestParam Integer recompensa
    ) {
        return ResponseEntity.ok(misionService.buscarPorRecompensa(recompensa));
    }
}