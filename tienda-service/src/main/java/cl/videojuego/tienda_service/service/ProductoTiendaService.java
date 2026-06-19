package cl.videojuego.tienda_service.service;

import cl.videojuego.tienda_service.client.ArmaClient;
import cl.videojuego.tienda_service.dto.ArmaDTO;
import cl.videojuego.tienda_service.dto.ProductoTiendaDTO;
import cl.videojuego.tienda_service.dto.ProductoTiendaRegistroDTO;
import cl.videojuego.tienda_service.exception.PrecioNegativoException;
import cl.videojuego.tienda_service.exception.ProductoNoEncontradoException;
import cl.videojuego.tienda_service.exception.RecursoNoEncontradoException;
import cl.videojuego.tienda_service.exception.StockNegativoException;
import cl.videojuego.tienda_service.mapper.ProductoTiendaMapper;
import cl.videojuego.tienda_service.model.CategoriaProducto;
import cl.videojuego.tienda_service.model.EstadoProducto;
import cl.videojuego.tienda_service.model.ProductoTienda;
import cl.videojuego.tienda_service.model.TipoItem;
import cl.videojuego.tienda_service.repository.CategoriaProductoRepository;
import cl.videojuego.tienda_service.repository.EstadoProductoRepository;
import cl.videojuego.tienda_service.repository.ProductoTiendaRepository;
import cl.videojuego.tienda_service.repository.TipoItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("null")
@RequiredArgsConstructor
public class ProductoTiendaService {

    private final ProductoTiendaRepository productoTiendaRepository;
    private final CategoriaProductoRepository categoriaProductoRepository;
    private final EstadoProductoRepository estadoProductoRepository;
    private final TipoItemRepository tipoItemRepository;
    private final ArmaClient armaClient;
    private final ProductoTiendaMapper productoTiendaMapper;


    public List<ProductoTiendaDTO> listarTodos() {
        return mapearListaProductos(productoTiendaRepository.findAll());
    }

    public ProductoTiendaDTO registrar(ProductoTiendaRegistroDTO dto) {

        if (dto.getPrecio() < 0) {
            throw new PrecioNegativoException(
                    "El precio del producto no puede ser negativo. Valor recibido: " + dto.getPrecio());
        }
        if (dto.getStock() < 0) {
            throw new StockNegativoException(
                    "El stock del producto no puede ser negativo. Valor recibido: " + dto.getStock());
        }

        ArmaDTO arma = obtenerArmaSeguro(dto.getIdReferenciaItem());

        CategoriaProducto categoria = categoriaProductoRepository.findById(dto.getIdCategoriaProducto())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Categoría no encontrada con id: " + dto.getIdCategoriaProducto()));

        EstadoProducto estado = estadoProductoRepository.findById(dto.getIdEstadoProducto())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Estado de producto no encontrado con id: " + dto.getIdEstadoProducto()));

        TipoItem tipoItem = tipoItemRepository.findById(dto.getIdTipoItem())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Tipo de item no encontrado con id: " + dto.getIdTipoItem()));

        ProductoTienda producto = productoTiendaMapper.toEntity(dto, categoria, estado, tipoItem);

        ProductoTienda guardado = productoTiendaRepository.save(producto);

        return productoTiendaMapper.toDTO(guardado, arma);
    }

    public List<ProductoTiendaDTO> listarPorCategoria(Long idCategoriaProducto) {
        return mapearListaProductos(
                productoTiendaRepository.findByCategoriaProducto_IdCategoriaProducto(idCategoriaProducto));
    }

    public List<ProductoTiendaDTO> listarPorEstado(Long idEstadoProducto) {
        return mapearListaProductos(productoTiendaRepository.findByEstadoProducto_IdEstadoProducto(idEstadoProducto));
    }

    public List<ProductoTiendaDTO> buscarPorPrecio(Integer precio) {
        return mapearListaProductos(productoTiendaRepository.findByPrecioLessThanEqual(precio));
    }

    public List<ProductoTiendaDTO> buscarPorNombre(String nombre) {
        return mapearListaProductos(productoTiendaRepository.findByNombreProductoContainingIgnoreCase(nombre));
    }

    public List<ProductoTiendaDTO> listarConStockMayorA(Integer stock) {
        return mapearListaProductos(productoTiendaRepository.findByStockGreaterThan(stock));
    }

    public ProductoTiendaDTO buscarPorId(Long idProducto) {

        ProductoTienda producto = productoTiendaRepository.findById(idProducto)
                .orElseThrow(() -> new ProductoNoEncontradoException(idProducto));

        ArmaDTO arma = obtenerArmaSeguro(producto.getIdReferenciaItem());

        return productoTiendaMapper.toDTO(producto, arma);
    }

    private List<ProductoTiendaDTO> mapearListaProductos(List<ProductoTienda> productos) {
        return productos.stream()
                .map(producto -> {
                    ArmaDTO arma = obtenerArmaSeguro(producto.getIdReferenciaItem());
                    return productoTiendaMapper.toDTO(producto, arma);
                })
                .collect(Collectors.toList());
    }

    private ArmaDTO obtenerArmaSeguro(Long idReferenciaItem) {
        try {
            return armaClient.buscarArmaPorId(idReferenciaItem);
        } catch (Exception e) {
            // Tolerancia a fallos: Evita que la tienda se caiga si el servicio de armas no responde
            ArmaDTO armaFallback = new ArmaDTO();
            armaFallback.setIdArma(idReferenciaItem);
            armaFallback.setNombreArma("Información no disponible temporalmente");
            return armaFallback;
        }
    }
}