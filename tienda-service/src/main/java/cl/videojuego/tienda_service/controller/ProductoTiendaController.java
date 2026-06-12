package cl.videojuego.tienda_service.controller;

import cl.videojuego.tienda_service.dto.ProductoTiendaDTO;
import cl.videojuego.tienda_service.dto.ProductoTiendaRegistroDTO;
import cl.videojuego.tienda_service.service.ProductoTiendaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoTiendaController {

    private final ProductoTiendaService productoTiendaService;

    public ProductoTiendaController(
            ProductoTiendaService productoTiendaService
    ) {
        this.productoTiendaService = productoTiendaService;
    }

    // GET: listar todos los productos
    @GetMapping
    public ResponseEntity<List<ProductoTiendaDTO>> listarTodos() {
        return ResponseEntity.ok(productoTiendaService.listarTodos());
    }

    // POST: registrar producto en tienda
    @PostMapping
    public ResponseEntity<ProductoTiendaDTO> registrar(
            @Valid @RequestBody ProductoTiendaRegistroDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoTiendaService.registrar(dto));
    }

    // GET: buscar productos por categoría
    @GetMapping("/categoria/{idCategoriaProducto}")
    public ResponseEntity<List<ProductoTiendaDTO>> listarPorCategoria(
            @PathVariable Long idCategoriaProducto
    ) {
        return ResponseEntity.ok(productoTiendaService.listarPorCategoria(idCategoriaProducto));
    }

    // GET: buscar productos por estado
    @GetMapping("/estado/{idEstadoProducto}")
    public ResponseEntity<List<ProductoTiendaDTO>> listarPorEstado(
            @PathVariable Long idEstadoProducto
    ) {
        return ResponseEntity.ok(productoTiendaService.listarPorEstado(idEstadoProducto));
    }

    // GET: buscar productos por precio máximo
    @GetMapping("/precio-menor")
    public ResponseEntity<List<ProductoTiendaDTO>> buscarPorPrecio(
            @RequestParam Integer precio
    ) {
        return ResponseEntity.ok(productoTiendaService.buscarPorPrecio(precio));
    }

    // GET: buscar productos por nombre
    @GetMapping("/buscar-por-nombre")
    public ResponseEntity<List<ProductoTiendaDTO>> buscarPorNombre(
            @RequestParam String nombre
    ) {
        return ResponseEntity.ok(productoTiendaService.buscarPorNombre(nombre));
    }

    // GET: productos con stock mayor a cierto valor
    @GetMapping("/stock-mayor")
    public ResponseEntity<List<ProductoTiendaDTO>> listarConStockMayorA(
            @RequestParam Integer stock
    ) {
        return ResponseEntity.ok(productoTiendaService.listarConStockMayorA(stock));
    }
    @GetMapping("/{idProducto}")
    public ResponseEntity<ProductoTiendaDTO> buscarPorId(
            @PathVariable Long idProducto
    ) {
        return ResponseEntity.ok(productoTiendaService.buscarPorId(idProducto));
    }

}