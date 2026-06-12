package cl.videojuego.inventario_service.controller;

import cl.videojuego.inventario_service.dto.ItemInventarioDTO;
import cl.videojuego.inventario_service.dto.ItemInventarioRegistroDTO;
import cl.videojuego.inventario_service.service.ItemInventarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items-inventario")
public class ItemInventarioController {

    private final ItemInventarioService itemInventarioService;

    public ItemInventarioController(
            ItemInventarioService itemInventarioService
    ) {
        this.itemInventarioService = itemInventarioService;
    }

    @GetMapping
    public ResponseEntity<List<ItemInventarioDTO>> listarTodos() {
        return ResponseEntity.ok(itemInventarioService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<ItemInventarioDTO> registrar(
            @Valid @RequestBody ItemInventarioRegistroDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemInventarioService.registrar(dto));
    }

    @GetMapping("/inventario/{idInventario}")
    public ResponseEntity<List<ItemInventarioDTO>> listarPorInventario(
            @PathVariable Long idInventario
    ) {
        return ResponseEntity.ok(itemInventarioService.listarPorInventario(idInventario));
    }

    @GetMapping("/referencia/{idReferencia}")
    public ResponseEntity<List<ItemInventarioDTO>> listarPorReferencia(
            @PathVariable Long idReferencia
    ) {
        return ResponseEntity.ok(itemInventarioService.listarPorReferencia(idReferencia));
    }

    @GetMapping("/equipados")
    public ResponseEntity<List<ItemInventarioDTO>> listarEquipados(
            @RequestParam Boolean equipado
    ) {
        return ResponseEntity.ok(itemInventarioService.listarEquipados(equipado));
    }
}