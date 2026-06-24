package cl.videojuego.tienda_service.controller;

import cl.videojuego.tienda_service.dto.ProductoTiendaDTO;
import cl.videojuego.tienda_service.dto.ProductoTiendaRegistroDTO;
import cl.videojuego.tienda_service.service.ProductoTiendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Tag(
        name = "Productos de Tienda",
        description = "API para la gestión de productos disponibles en la tienda del videojuego"
)
public class ProductoTiendaController {

    private final ProductoTiendaService productoTiendaService;

    public ProductoTiendaController(ProductoTiendaService productoTiendaService) {
        this.productoTiendaService = productoTiendaService;
    }

    @Operation(
            summary = "Listar todos los productos",
            description = "Retorna una lista completa con todos los productos registrados en la tienda del videojuego."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Productos listados correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ProductoTiendaDTO.class)
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
    public ResponseEntity<List<ProductoTiendaDTO>> listarTodos() {
        return ResponseEntity.ok(productoTiendaService.listarTodos());
    }

    @Operation(
            summary = "Registrar producto en tienda",
            description = "Permite registrar un nuevo producto en la tienda del videojuego. El producto puede estar asociado a un arma y se clasifica mediante categoría y estado."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Producto registrado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductoTiendaDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos enviados en la solicitud",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Arma, categoría o estado de producto no encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<ProductoTiendaDTO> registrar(
            @Valid @RequestBody ProductoTiendaRegistroDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productoTiendaService.registrar(dto));
    }

    @Operation(
            summary = "Listar productos por categoría",
            description = "Retorna todos los productos asociados a una categoría específica de la tienda."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Productos por categoría listados correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ProductoTiendaDTO.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping("/categoria/{idCategoriaProducto}")
    public ResponseEntity<List<ProductoTiendaDTO>> listarPorCategoria(
            @Parameter(
                    description = "Identificador de la categoría del producto",
                    example = "1"
            )
            @PathVariable Long idCategoriaProducto
    ) {
        return ResponseEntity.ok(productoTiendaService.listarPorCategoria(idCategoriaProducto));
    }

    @Operation(
            summary = "Listar productos por estado",
            description = "Retorna todos los productos asociados a un estado específico, por ejemplo disponible, agotado o inactivo."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Productos por estado listados correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ProductoTiendaDTO.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping("/estado/{idEstadoProducto}")
    public ResponseEntity<List<ProductoTiendaDTO>> listarPorEstado(
            @Parameter(
                    description = "Identificador del estado del producto",
                    example = "1"
            )
            @PathVariable Long idEstadoProducto
    ) {
        return ResponseEntity.ok(productoTiendaService.listarPorEstado(idEstadoProducto));
    }

    @Operation(
            summary = "Buscar productos por precio máximo",
            description = "Retorna productos cuyo precio sea menor o igual al valor indicado."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Productos filtrados por precio obtenidos correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ProductoTiendaDTO.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Precio inválido",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping("/precio-menor")
    public ResponseEntity<List<ProductoTiendaDTO>> buscarPorPrecio(
            @Parameter(
                    description = "Precio máximo permitido para filtrar productos",
                    example = "10000"
            )
            @RequestParam Integer precio
    ) {
        return ResponseEntity.ok(productoTiendaService.buscarPorPrecio(precio));
    }

    @Operation(
            summary = "Buscar productos por nombre",
            description = "Permite buscar productos utilizando una parte del nombre, sin distinguir mayúsculas o minúsculas."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Productos encontrados correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ProductoTiendaDTO.class)
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
    public ResponseEntity<List<ProductoTiendaDTO>> buscarPorNombre(
            @Parameter(
                    description = "Nombre o parte del nombre del producto",
                    example = "Espada"
            )
            @RequestParam String nombre
    ) {
        return ResponseEntity.ok(productoTiendaService.buscarPorNombre(nombre));
    }

    @Operation(
            summary = "Listar productos con stock mayor a un valor",
            description = "Retorna productos cuyo stock sea mayor al valor indicado."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Productos filtrados por stock obtenidos correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ProductoTiendaDTO.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Stock inválido",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping("/stock-mayor")
    public ResponseEntity<List<ProductoTiendaDTO>> listarConStockMayorA(
            @Parameter(
                    description = "Stock mínimo requerido para filtrar productos",
                    example = "5"
            )
            @RequestParam Integer stock
    ) {
        return ResponseEntity.ok(productoTiendaService.listarConStockMayorA(stock));
    }

    @Operation(
            summary = "Buscar producto por ID",
            description = "Permite obtener la información detallada de un producto específico mediante su identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Producto encontrado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductoTiendaDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Producto no encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    @GetMapping("/{idProducto}")
    public ResponseEntity<ProductoTiendaDTO> buscarPorId(
            @Parameter(
                    description = "Identificador único del producto",
                    example = "1"
            )
            @PathVariable Long idProducto
    ) {
        return ResponseEntity.ok(productoTiendaService.buscarPorId(idProducto));
    }
}