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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class ProductoTiendaServiceTest {

    @Mock
    private ProductoTiendaRepository productoTiendaRepository;

    @Mock
    private CategoriaProductoRepository categoriaProductoRepository;

    @Mock
    private EstadoProductoRepository estadoProductoRepository;

    @Mock
    private TipoItemRepository tipoItemRepository;

    @Mock
    private ArmaClient armaClient;

    @Mock
    private ProductoTiendaMapper productoTiendaMapper;

    @InjectMocks
    private ProductoTiendaService productoTiendaService;

    @Test
    void shouldListAllProducts_WhenProductsExist() {
        // Given
        Long idReferenciaItem = 100L;
        ProductoTienda producto = new ProductoTienda();
        producto.setIdProducto(1L);
        producto.setIdReferenciaItem(idReferenciaItem);

        ArmaDTO armaDTO = new ArmaDTO();
        armaDTO.setIdArma(idReferenciaItem);

        ProductoTiendaDTO productoDTO = new ProductoTiendaDTO();
        productoDTO.setIdProducto(1L);

        given(productoTiendaRepository.findAll()).willReturn(List.of(producto));
        given(armaClient.buscarArmaPorId(idReferenciaItem)).willReturn(armaDTO);
        given(productoTiendaMapper.toDTO(producto, armaDTO)).willReturn(productoDTO);

        // When
        List<ProductoTiendaDTO> result = productoTiendaService.listarTodos();

        // Then
        assertThat(result).isNotNull().hasSize(1).containsExactly(productoDTO);
        
        verify(productoTiendaRepository, times(1)).findAll();
        verify(armaClient, times(1)).buscarArmaPorId(idReferenciaItem);
        verify(productoTiendaMapper, times(1)).toDTO(producto, armaDTO);
        verifyNoMoreInteractions(productoTiendaRepository, armaClient, productoTiendaMapper);
    }

    @Test
    void shouldRegisterProduct_WhenValidDataProvided() {
        // Given
        ProductoTiendaRegistroDTO dto = new ProductoTiendaRegistroDTO();
        dto.setPrecio(1500);
        dto.setStock(20);
        dto.setIdReferenciaItem(100L);
        dto.setIdCategoriaProducto(1L);
        dto.setIdEstadoProducto(2L);
        dto.setIdTipoItem(3L);

        ArmaDTO armaDTO = new ArmaDTO();
        CategoriaProducto categoria = new CategoriaProducto();
        EstadoProducto estado = new EstadoProducto();
        TipoItem tipoItem = new TipoItem();
        ProductoTienda productoEntity = new ProductoTienda();
        ProductoTienda guardadoEntity = new ProductoTienda();
        ProductoTiendaDTO expectedDto = new ProductoTiendaDTO();

        given(armaClient.buscarArmaPorId(100L)).willReturn(armaDTO);
        given(categoriaProductoRepository.findById(1L)).willReturn(Optional.of(categoria));
        given(estadoProductoRepository.findById(2L)).willReturn(Optional.of(estado));
        given(tipoItemRepository.findById(3L)).willReturn(Optional.of(tipoItem));
        given(productoTiendaMapper.toEntity(dto, categoria, estado, tipoItem)).willReturn(productoEntity);
        given(productoTiendaRepository.save(productoEntity)).willReturn(guardadoEntity);
        given(productoTiendaMapper.toDTO(guardadoEntity, armaDTO)).willReturn(expectedDto);

        // When
        ProductoTiendaDTO result = productoTiendaService.registrar(dto);

        // Then
        assertThat(result).isEqualTo(expectedDto);

        verify(armaClient, times(1)).buscarArmaPorId(100L);
        verify(categoriaProductoRepository, times(1)).findById(1L);
        verify(estadoProductoRepository, times(1)).findById(2L);
        verify(tipoItemRepository, times(1)).findById(3L);
        verify(productoTiendaMapper, times(1)).toEntity(dto, categoria, estado, tipoItem);
        verify(productoTiendaRepository, times(1)).save(productoEntity);
        verify(productoTiendaMapper, times(1)).toDTO(guardadoEntity, armaDTO);
    }

    @Test
    void shouldThrowPrecioNegativoException_WhenRegisteringWithNegativePrice() {
        // Given
        ProductoTiendaRegistroDTO dto = new ProductoTiendaRegistroDTO();
        dto.setPrecio(-10);

        // When / Then
        assertThatThrownBy(() -> productoTiendaService.registrar(dto))
                .isInstanceOf(PrecioNegativoException.class)
                .hasMessageContaining("El precio del producto no puede ser negativo");

        verifyNoInteractions(armaClient, categoriaProductoRepository, estadoProductoRepository, 
                tipoItemRepository, productoTiendaMapper, productoTiendaRepository);
    }

    @Test
    void shouldThrowStockNegativoException_WhenRegisteringWithNegativeStock() {
        // Given
        ProductoTiendaRegistroDTO dto = new ProductoTiendaRegistroDTO();
        dto.setPrecio(100);
        dto.setStock(-5);

        // When / Then
        assertThatThrownBy(() -> productoTiendaService.registrar(dto))
                .isInstanceOf(StockNegativoException.class)
                .hasMessageContaining("El stock del producto no puede ser negativo");

        verifyNoInteractions(armaClient, categoriaProductoRepository, estadoProductoRepository, 
                tipoItemRepository, productoTiendaMapper, productoTiendaRepository);
    }

    @Test
    void shouldRegisterUsingFallbackArma_WhenArmaClientThrowsException() {
        // Given
        ProductoTiendaRegistroDTO dto = new ProductoTiendaRegistroDTO();
        dto.setPrecio(100);
        dto.setStock(10);
        dto.setIdReferenciaItem(100L);
        dto.setIdCategoriaProducto(1L);
        dto.setIdEstadoProducto(2L);
        dto.setIdTipoItem(3L);

        CategoriaProducto categoria = new CategoriaProducto();
        EstadoProducto estado = new EstadoProducto();
        TipoItem tipoItem = new TipoItem();
        ProductoTienda productoEntity = new ProductoTienda();
        ProductoTienda guardadoEntity = new ProductoTienda();
        ProductoTiendaDTO expectedDto = new ProductoTiendaDTO();

        given(armaClient.buscarArmaPorId(100L)).willThrow(new RuntimeException("Error connection refused"));
        given(categoriaProductoRepository.findById(1L)).willReturn(Optional.of(categoria));
        given(estadoProductoRepository.findById(2L)).willReturn(Optional.of(estado));
        given(tipoItemRepository.findById(3L)).willReturn(Optional.of(tipoItem));
        given(productoTiendaMapper.toEntity(dto, categoria, estado, tipoItem)).willReturn(productoEntity);
        given(productoTiendaRepository.save(productoEntity)).willReturn(guardadoEntity);
        given(productoTiendaMapper.toDTO(any(ProductoTienda.class), any(ArmaDTO.class))).willReturn(expectedDto);

        // When
        ProductoTiendaDTO result = productoTiendaService.registrar(dto);

        // Then
        assertThat(result).isEqualTo(expectedDto);
        verify(armaClient, times(1)).buscarArmaPorId(100L);
        verify(productoTiendaRepository, times(1)).save(productoEntity);
    }

    @Test
    void shouldThrowRecursoNoEncontradoException_WhenCategoriaNotFound() {
        // Given
        ProductoTiendaRegistroDTO dto = new ProductoTiendaRegistroDTO();
        dto.setPrecio(100);
        dto.setStock(10);
        dto.setIdReferenciaItem(100L);
        dto.setIdCategoriaProducto(99L);

        ArmaDTO armaDTO = new ArmaDTO();
        given(armaClient.buscarArmaPorId(100L)).willReturn(armaDTO);
        given(categoriaProductoRepository.findById(99L)).willReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> productoTiendaService.registrar(dto))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Categoría no encontrada con id: 99");

        verify(estadoProductoRepository, times(0)).findById(any());
        verify(productoTiendaRepository, times(0)).save(any());
    }

    @Test
    void shouldThrowRecursoNoEncontradoException_WhenEstadoNotFound() {
        // Given
        ProductoTiendaRegistroDTO dto = new ProductoTiendaRegistroDTO();
        dto.setPrecio(100);
        dto.setStock(10);
        dto.setIdReferenciaItem(100L);
        dto.setIdCategoriaProducto(1L);
        dto.setIdEstadoProducto(99L);

        ArmaDTO armaDTO = new ArmaDTO();
        CategoriaProducto categoria = new CategoriaProducto();
        
        given(armaClient.buscarArmaPorId(100L)).willReturn(armaDTO);
        given(categoriaProductoRepository.findById(1L)).willReturn(Optional.of(categoria));
        given(estadoProductoRepository.findById(99L)).willReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> productoTiendaService.registrar(dto))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Estado de producto no encontrado con id: 99");

        verify(tipoItemRepository, times(0)).findById(any());
        verify(productoTiendaRepository, times(0)).save(any());
    }

    @Test
    void shouldThrowRecursoNoEncontradoException_WhenTipoItemNotFound() {
        // Given
        ProductoTiendaRegistroDTO dto = new ProductoTiendaRegistroDTO();
        dto.setPrecio(100);
        dto.setStock(10);
        dto.setIdReferenciaItem(100L);
        dto.setIdCategoriaProducto(1L);
        dto.setIdEstadoProducto(2L);
        dto.setIdTipoItem(99L);

        ArmaDTO armaDTO = new ArmaDTO();
        CategoriaProducto categoria = new CategoriaProducto();
        EstadoProducto estado = new EstadoProducto();
        
        given(armaClient.buscarArmaPorId(100L)).willReturn(armaDTO);
        given(categoriaProductoRepository.findById(1L)).willReturn(Optional.of(categoria));
        given(estadoProductoRepository.findById(2L)).willReturn(Optional.of(estado));
        given(tipoItemRepository.findById(99L)).willReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> productoTiendaService.registrar(dto))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Tipo de item no encontrado con id: 99");

        verify(productoTiendaRepository, times(0)).save(any());
    }

    @Test
    void shouldFindProductById_WhenProductExists() {
        // Given
        Long idProducto = 1L;
        Long idReferencia = 10L;
        ProductoTienda producto = new ProductoTienda();
        producto.setIdProducto(idProducto);
        producto.setIdReferenciaItem(idReferencia);
        
        ArmaDTO arma = new ArmaDTO();
        ProductoTiendaDTO dto = new ProductoTiendaDTO();

        given(productoTiendaRepository.findById(idProducto)).willReturn(Optional.of(producto));
        given(armaClient.buscarArmaPorId(idReferencia)).willReturn(arma);
        given(productoTiendaMapper.toDTO(producto, arma)).willReturn(dto);

        // When
        ProductoTiendaDTO result = productoTiendaService.buscarPorId(idProducto);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(productoTiendaRepository, times(1)).findById(idProducto);
        verify(armaClient, times(1)).buscarArmaPorId(idReferencia);
        verify(productoTiendaMapper, times(1)).toDTO(producto, arma);
    }

    @Test
    void shouldThrowProductoNoEncontradoException_WhenFindByIdNotFound() {
        // Given
        Long idProducto = 1L;
        given(productoTiendaRepository.findById(idProducto)).willReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> productoTiendaService.buscarPorId(idProducto))
                .isInstanceOf(ProductoNoEncontradoException.class);
        
        verify(productoTiendaRepository, times(1)).findById(idProducto);
        verifyNoInteractions(armaClient, productoTiendaMapper);
    }

    @Test
    void shouldListProductsByCategoria() {
        // Given
        Long idCategoria = 1L;
        ProductoTienda producto = new ProductoTienda();
        producto.setIdReferenciaItem(10L);
        ArmaDTO arma = new ArmaDTO();
        ProductoTiendaDTO dto = new ProductoTiendaDTO();

        given(productoTiendaRepository.findByCategoriaProducto_IdCategoriaProducto(idCategoria)).willReturn(List.of(producto));
        given(armaClient.buscarArmaPorId(10L)).willReturn(arma);
        given(productoTiendaMapper.toDTO(producto, arma)).willReturn(dto);

        // When
        List<ProductoTiendaDTO> result = productoTiendaService.listarPorCategoria(idCategoria);

        // Then
        assertThat(result).hasSize(1).containsExactly(dto);
        verify(productoTiendaRepository, times(1)).findByCategoriaProducto_IdCategoriaProducto(idCategoria);
    }

    @Test
    void shouldListProductsByEstado() {
        // Given
        Long idEstado = 2L;
        ProductoTienda producto = new ProductoTienda();
        producto.setIdReferenciaItem(10L);
        ArmaDTO arma = new ArmaDTO();
        ProductoTiendaDTO dto = new ProductoTiendaDTO();

        given(productoTiendaRepository.findByEstadoProducto_IdEstadoProducto(idEstado)).willReturn(List.of(producto));
        given(armaClient.buscarArmaPorId(10L)).willReturn(arma);
        given(productoTiendaMapper.toDTO(producto, arma)).willReturn(dto);

        // When
        List<ProductoTiendaDTO> result = productoTiendaService.listarPorEstado(idEstado);

        // Then
        assertThat(result).hasSize(1).containsExactly(dto);
        verify(productoTiendaRepository, times(1)).findByEstadoProducto_IdEstadoProducto(idEstado);
    }

    @Test
    void shouldListProductsByPriceLessThanEqual() {
        // Given
        Integer precio = 500;
        ProductoTienda producto = new ProductoTienda();
        producto.setIdReferenciaItem(10L);
        ArmaDTO arma = new ArmaDTO();
        ProductoTiendaDTO dto = new ProductoTiendaDTO();

        given(productoTiendaRepository.findByPrecioLessThanEqual(precio)).willReturn(List.of(producto));
        given(armaClient.buscarArmaPorId(10L)).willReturn(arma);
        given(productoTiendaMapper.toDTO(producto, arma)).willReturn(dto);

        // When
        List<ProductoTiendaDTO> result = productoTiendaService.buscarPorPrecio(precio);

        // Then
        assertThat(result).hasSize(1).containsExactly(dto);
        verify(productoTiendaRepository, times(1)).findByPrecioLessThanEqual(precio);
    }

    @Test
    void shouldListProductsByNameContaining() {
        // Given
        String nombre = "Espada";
        ProductoTienda producto = new ProductoTienda();
        producto.setIdReferenciaItem(10L);
        ArmaDTO arma = new ArmaDTO();
        ProductoTiendaDTO dto = new ProductoTiendaDTO();

        given(productoTiendaRepository.findByNombreProductoContainingIgnoreCase(nombre)).willReturn(List.of(producto));
        given(armaClient.buscarArmaPorId(10L)).willReturn(arma);
        given(productoTiendaMapper.toDTO(producto, arma)).willReturn(dto);

        // When
        List<ProductoTiendaDTO> result = productoTiendaService.buscarPorNombre(nombre);

        // Then
        assertThat(result).hasSize(1).containsExactly(dto);
        verify(productoTiendaRepository, times(1)).findByNombreProductoContainingIgnoreCase(nombre);
    }

    @Test
    void shouldListProductsWithStockGreaterThan() {
        // Given
        Integer stock = 0;
        ProductoTienda producto = new ProductoTienda();
        producto.setIdReferenciaItem(10L);
        ArmaDTO arma = new ArmaDTO();
        ProductoTiendaDTO dto = new ProductoTiendaDTO();

        given(productoTiendaRepository.findByStockGreaterThan(stock)).willReturn(List.of(producto));
        given(armaClient.buscarArmaPorId(10L)).willReturn(arma);
        given(productoTiendaMapper.toDTO(producto, arma)).willReturn(dto);

        // When
        List<ProductoTiendaDTO> result = productoTiendaService.listarConStockMayorA(stock);

        // Then
        assertThat(result).hasSize(1).containsExactly(dto);
        verify(productoTiendaRepository, times(1)).findByStockGreaterThan(stock);
    }
}
