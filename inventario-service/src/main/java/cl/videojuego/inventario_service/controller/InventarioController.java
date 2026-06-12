package cl.videojuego.inventario_service.controller;

import cl.videojuego.inventario_service.dto.InventarioDTO;
import cl.videojuego.inventario_service.dto.InventarioRegistroDTO;
import cl.videojuego.inventario_service.service.InventarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping
    public ResponseEntity<List<InventarioDTO>> listarTodos() {
        return ResponseEntity.ok(inventarioService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<InventarioDTO> registrar(
            @Valid @RequestBody InventarioRegistroDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.registrar(dto));
    }

    @GetMapping("/personaje/{idPersonaje}")
    public ResponseEntity<List<InventarioDTO>> listarPorPersonaje(
            @PathVariable Long idPersonaje
    ) {
        return ResponseEntity.ok(inventarioService.listarPorPersonaje(idPersonaje));
    }

    @GetMapping("/estado/{idEstadoInventario}")
    public ResponseEntity<List<InventarioDTO>> listarPorEstado(
            @PathVariable Long idEstadoInventario
    ) {
        return ResponseEntity.ok(inventarioService.listarPorEstado(idEstadoInventario));
    }
    @GetMapping("/{idInventario}")
    public ResponseEntity<InventarioDTO> buscarPorId(
            @PathVariable Long idInventario
    ) {
        return ResponseEntity.ok(inventarioService.buscarPorId(idInventario));
    }
}