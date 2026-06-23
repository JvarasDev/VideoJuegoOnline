package cl.videojuego.tienda_service.controller;

import cl.videojuego.tienda_service.dto.ProductoTiendaDTO;
import cl.videojuego.tienda_service.dto.ProductoTiendaRegistroDTO;
import cl.videojuego.tienda_service.exception.PrecioNegativoException;
import cl.videojuego.tienda_service.exception.ProductoNoEncontradoException;
import cl.videojuego.tienda_service.service.ProductoTiendaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductoTiendaController.class)
class ProductoTiendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductoTiendaService productoTiendaService;

    @Test
    void shouldReturn200AndProductList_WhenListarTodosIsCalled() throws Exception {
        // Given
        ProductoTiendaDTO producto = new ProductoTiendaDTO();
        producto.setIdProducto(1L);
        producto.setNombreProducto("Espada Legendaria");
        producto.setPrecio(1000);

        List<ProductoTiendaDTO> lista = List.of(producto);

        given(productoTiendaService.listarTodos()).willReturn(lista);

        // When & Then
        mockMvc.perform(get("/api/productos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].idProducto").value(1))
                .andExpect(jsonPath("$[0].nombreProducto").value("Espada Legendaria"))
                .andExpect(jsonPath("$[0].precio").value(1000));
    }

    @Test
    void shouldReturn201AndCreatedProduct_WhenRegistrarIsCalledWithValidPayload() throws Exception {
        // Given
        ProductoTiendaRegistroDTO dto = new ProductoTiendaRegistroDTO();
        dto.setPrecio(1500);
        dto.setStock(20);
        dto.setIdReferenciaItem(10L);
        dto.setIdCategoriaProducto(1L);
        dto.setIdEstadoProducto(1L);
        dto.setIdTipoItem(1L);

        ProductoTiendaDTO productoCreado = new ProductoTiendaDTO();
        productoCreado.setIdProducto(1L);
        productoCreado.setPrecio(1500);
        productoCreado.setStock(20);
        productoCreado.setNombreProducto("Arma Creada");

        given(productoTiendaService.registrar(any(ProductoTiendaRegistroDTO.class))).willReturn(productoCreado);

        // When & Then
        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idProducto").value(1))
                .andExpect(jsonPath("$.nombreProducto").value("Arma Creada"))
                .andExpect(jsonPath("$.precio").value(1500));
    }

    @Test
    void shouldReturn200AndProductList_WhenListarPorCategoriaIsCalled() throws Exception {
        // Given
        Long idCategoria = 1L;
        ProductoTiendaDTO producto = new ProductoTiendaDTO();
        producto.setIdProducto(1L);
        producto.setNombreProducto("Item Categoria 1");

        given(productoTiendaService.listarPorCategoria(idCategoria)).willReturn(List.of(producto));

        // When & Then
        mockMvc.perform(get("/api/productos/categoria/{idCategoriaProducto}", idCategoria)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nombreProducto").value("Item Categoria 1"));
    }

    @Test
    void shouldReturn200AndProductList_WhenListarPorEstadoIsCalled() throws Exception {
        // Given
        Long idEstado = 2L;
        ProductoTiendaDTO producto = new ProductoTiendaDTO();
        producto.setIdProducto(1L);
        producto.setNombreProducto("Item Estado 2");

        given(productoTiendaService.listarPorEstado(idEstado)).willReturn(List.of(producto));

        // When & Then
        mockMvc.perform(get("/api/productos/estado/{idEstadoProducto}", idEstado)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nombreProducto").value("Item Estado 2"));
    }

    @Test
    void shouldReturn200AndProductList_WhenBuscarPorPrecioIsCalled() throws Exception {
        // Given
        Integer precio = 500;
        ProductoTiendaDTO producto = new ProductoTiendaDTO();
        producto.setIdProducto(1L);
        producto.setPrecio(400);

        given(productoTiendaService.buscarPorPrecio(precio)).willReturn(List.of(producto));

        // When & Then
        mockMvc.perform(get("/api/productos/precio-menor")
                .param("precio", precio.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].precio").value(400));
    }

    @Test
    void shouldReturn200AndProductList_WhenBuscarPorNombreIsCalled() throws Exception {
        // Given
        String nombre = "Espada";
        ProductoTiendaDTO producto = new ProductoTiendaDTO();
        producto.setIdProducto(1L);
        producto.setNombreProducto("Espada Larga");

        given(productoTiendaService.buscarPorNombre(nombre)).willReturn(List.of(producto));

        // When & Then
        mockMvc.perform(get("/api/productos/buscar-por-nombre")
                .param("nombre", nombre)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nombreProducto").value("Espada Larga"));
    }

    @Test
    void shouldReturn200AndProductList_WhenListarConStockMayorAIsCalled() throws Exception {
        // Given
        Integer stock = 5;
        ProductoTiendaDTO producto = new ProductoTiendaDTO();
        producto.setIdProducto(1L);
        producto.setStock(10);

        given(productoTiendaService.listarConStockMayorA(stock)).willReturn(List.of(producto));

        // When & Then
        mockMvc.perform(get("/api/productos/stock-mayor")
                .param("stock", stock.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].stock").value(10));
    }

    @Test
    void shouldReturn200AndProduct_WhenBuscarPorIdIsCalledWithExistingId() throws Exception {
        // Given
        Long idProducto = 1L;
        ProductoTiendaDTO producto = new ProductoTiendaDTO();
        producto.setIdProducto(idProducto);
        producto.setNombreProducto("Item Encontrado");

        given(productoTiendaService.buscarPorId(idProducto)).willReturn(producto);

        // When & Then
        mockMvc.perform(get("/api/productos/{idProducto}", idProducto)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idProducto").value(idProducto))
                .andExpect(jsonPath("$.nombreProducto").value("Item Encontrado"));
    }

    @Test
    void shouldReturn404NotFound_WhenBuscarPorIdIsCalledAndProductDoesNotExist() throws Exception {
        // Given
        Long idProducto = 99L;
        given(productoTiendaService.buscarPorId(idProducto))
                .willThrow(new ProductoNoEncontradoException(idProducto));

        // When & Then
        mockMvc.perform(get("/api/productos/{idProducto}", idProducto)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigoHTTP").value("NOT_FOUND"))
                .andExpect(jsonPath("$.codigoError").exists())
                .andExpect(jsonPath("$.mensaje").exists());
    }

    @Test
    void shouldReturn400BadRequest_WhenRegistrarIsCalledWithNegativePrice() throws Exception {
        // Given
        ProductoTiendaRegistroDTO dto = new ProductoTiendaRegistroDTO();
        dto.setPrecio(-500); // Invalid price
        dto.setStock(20);
        dto.setIdReferenciaItem(10L);
        dto.setIdCategoriaProducto(1L);
        dto.setIdEstadoProducto(1L);
        dto.setIdTipoItem(1L);

        given(productoTiendaService.registrar(any(ProductoTiendaRegistroDTO.class)))
                .willThrow(new PrecioNegativoException("El precio del producto no puede ser negativo"));

        // When & Then
        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigoHTTP").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.codigoError").value("PRECIO_NEGATIVO"))
                .andExpect(jsonPath("$.mensaje").value("El precio del producto no puede ser negativo"));
    }
}
