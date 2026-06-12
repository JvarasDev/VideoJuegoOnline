package cl.videojuego.arma_service.controller;

import cl.videojuego.arma_service.dto.ArmaDTO;
import cl.videojuego.arma_service.dto.ArmaRegistroDTO;
import cl.videojuego.arma_service.service.ArmaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/armas")
@RequiredArgsConstructor
public class ArmaController {

    private final ArmaService armaService;


    // GET: listar todas las armas
    @GetMapping
    public ResponseEntity<List<ArmaDTO>> listarTodas() {
        return ResponseEntity.ok(armaService.listarTodas());
    }

    // POST: crear una nueva arma
    @PostMapping
    public ResponseEntity<ArmaDTO> registrar(
            @Valid @RequestBody ArmaRegistroDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(armaService.registrar(dto));
    }

    // GET: buscar armas por tipo
    @GetMapping("/tipo/{idTipoArma}")
    public ResponseEntity<List<ArmaDTO>> listarPorTipo(
            @PathVariable Long idTipoArma
    ) {
        return ResponseEntity.ok(armaService.listarPorTipo(idTipoArma));
    }

    // GET: buscar armas por rareza
    @GetMapping("/rareza/{idRarezaArma}")
    public ResponseEntity<List<ArmaDTO>> listarPorRareza(
            @PathVariable Long idRarezaArma
    ) {
        return ResponseEntity.ok(armaService.listarPorRareza(idRarezaArma));
    }

    // GET: buscar armas por nivel mínimo exacto
    @GetMapping("/nivel/{nivelMinimo}")
    public ResponseEntity<List<ArmaDTO>> listarPorNivel(
            @PathVariable Integer nivelMinimo
    ) {
        return ResponseEntity.ok(armaService.listarPorNivel(nivelMinimo));
    }

    // GET: buscar armas por nombre parecido
    @GetMapping("/buscar-por-nombre")
    public ResponseEntity<List<ArmaDTO>> buscarPorNombre(
            @RequestParam String nombre
    ) {
        return ResponseEntity.ok(armaService.buscarPorNombre(nombre));
    }

    // GET: buscar armas con precio menor o igual
    @GetMapping("/precio-menor")
    public ResponseEntity<List<ArmaDTO>> buscarPorPrecio(
            @RequestParam Integer precio
    ) {
        return ResponseEntity.ok(armaService.buscarPorPrecio(precio));
    }
    // GET: buscar arma por ID
    @GetMapping("/{idArma}")
    public ResponseEntity<ArmaDTO> buscarPorId(
            @PathVariable Long idArma
    ) {
        return ResponseEntity.ok(armaService.buscarPorId(idArma));
    }
}