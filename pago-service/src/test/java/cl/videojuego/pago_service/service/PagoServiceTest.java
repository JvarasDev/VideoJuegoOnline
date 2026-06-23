package cl.videojuego.pago_service.service;

import cl.videojuego.pago_service.client.ProductoClient;
import cl.videojuego.pago_service.client.UsuarioClient;
import cl.videojuego.pago_service.dto.*;
import cl.videojuego.pago_service.exception.EstadoPagoNotFoundException;
import cl.videojuego.pago_service.exception.MetodoPagoNotFoundException;
import cl.videojuego.pago_service.exception.PagoNotFoundException;
import cl.videojuego.pago_service.exception.StockInsuficienteException;
import cl.videojuego.pago_service.model.EstadoPago;
import cl.videojuego.pago_service.model.MetodoPago;
import cl.videojuego.pago_service.model.Pago;
import cl.videojuego.pago_service.repository.EstadoPagoRepository;
import cl.videojuego.pago_service.repository.MetodoPagoRepository;
import cl.videojuego.pago_service.repository.PagoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private MetodoPagoRepository metodoPagoRepository;

    @Mock
    private EstadoPagoRepository estadoPagoRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private ProductoClient productoClient;

    @InjectMocks
    private PagoService pagoService;

    @Test
    void listarTodos_CuandoExistenPagos_DeberiaRetornarListaDePagoDTO() {
        // Given
        Pago pago = new Pago();
        pago.setIdUsuario(1L);
        pago.setIdProducto(1L);
        MetodoPago metodo = new MetodoPago();
        metodo.setNombreMetodo("Tarjeta");
        pago.setMetodoPago(metodo);
        EstadoPago estado = new EstadoPago();
        estado.setNombreEstado("Aprobado");
        pago.setEstadoPago(estado);
        
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        ProductoTiendaDTO productoDTO = new ProductoTiendaDTO();
        
        given(pagoRepository.findAll()).willReturn(List.of(pago));
        given(usuarioClient.buscarUsuarioPorId(1L)).willReturn(usuarioDTO);
        given(productoClient.buscarProductoPorId(1L)).willReturn(productoDTO);

        // When
        List<PagoDTO> resultado = pagoService.listarTodos();

        // Then
        assertThat(resultado).isNotNull().hasSize(1);
        
        verify(pagoRepository, times(1)).findAll();
        verify(usuarioClient, times(1)).buscarUsuarioPorId(1L);
        verify(productoClient, times(1)).buscarProductoPorId(1L);
        verifyNoMoreInteractions(pagoRepository, usuarioClient, productoClient);
    }

    @Test
    void listarPorUsuario_CuandoUsuarioTienePagos_DeberiaRetornarListaDePagoDTO() {
        // Given
        Long idUsuario = 1L;
        Pago pago = new Pago();
        pago.setIdUsuario(idUsuario);
        pago.setIdProducto(1L);
        MetodoPago metodo = new MetodoPago();
        metodo.setNombreMetodo("Tarjeta");
        pago.setMetodoPago(metodo);
        EstadoPago estado = new EstadoPago();
        estado.setNombreEstado("Aprobado");
        pago.setEstadoPago(estado);
        
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        ProductoTiendaDTO productoDTO = new ProductoTiendaDTO();

        given(pagoRepository.findByIdUsuario(idUsuario)).willReturn(List.of(pago));
        given(usuarioClient.buscarUsuarioPorId(idUsuario)).willReturn(usuarioDTO);
        given(productoClient.buscarProductoPorId(1L)).willReturn(productoDTO);

        // When
        List<PagoDTO> resultado = pagoService.listarPorUsuario(idUsuario);

        // Then
        assertThat(resultado).isNotNull().hasSize(1);
        
        verify(pagoRepository, times(1)).findByIdUsuario(idUsuario);
        verify(usuarioClient, times(1)).buscarUsuarioPorId(idUsuario);
        verify(productoClient, times(1)).buscarProductoPorId(1L);
        verifyNoMoreInteractions(pagoRepository);
    }

    @Test
    void listarPorEstado_CuandoExistenPagosConEstado_DeberiaRetornarListaDePagoDTO() {
        // Given
        Long idEstadoPago = 1L;
        Pago pago = new Pago();
        pago.setIdUsuario(1L);
        pago.setIdProducto(1L);
        MetodoPago metodo = new MetodoPago();
        metodo.setNombreMetodo("Tarjeta");
        pago.setMetodoPago(metodo);
        EstadoPago estado = new EstadoPago();
        estado.setNombreEstado("Aprobado");
        pago.setEstadoPago(estado);
        
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        ProductoTiendaDTO productoDTO = new ProductoTiendaDTO();

        given(pagoRepository.findByEstadoPago_IdEstadoPago(idEstadoPago)).willReturn(List.of(pago));
        given(usuarioClient.buscarUsuarioPorId(1L)).willReturn(usuarioDTO);
        given(productoClient.buscarProductoPorId(1L)).willReturn(productoDTO);

        // When
        List<PagoDTO> resultado = pagoService.listarPorEstado(idEstadoPago);

        // Then
        assertThat(resultado).isNotNull().hasSize(1);
        
        verify(pagoRepository, times(1)).findByEstadoPago_IdEstadoPago(idEstadoPago);
        verify(usuarioClient, times(1)).buscarUsuarioPorId(1L);
        verify(productoClient, times(1)).buscarProductoPorId(1L);
        verifyNoMoreInteractions(pagoRepository);
    }

    @Test
    void buscarPorId_CuandoPagoExiste_DeberiaRetornarPagoDTO() {
        // Given
        Long idPago = 1L;
        Pago pago = new Pago();
        pago.setIdUsuario(1L);
        pago.setIdProducto(1L);
        MetodoPago metodo = new MetodoPago();
        metodo.setNombreMetodo("Tarjeta");
        pago.setMetodoPago(metodo);
        EstadoPago estado = new EstadoPago();
        estado.setNombreEstado("Aprobado");
        pago.setEstadoPago(estado);
        
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        ProductoTiendaDTO productoDTO = new ProductoTiendaDTO();

        given(pagoRepository.findById(idPago)).willReturn(Optional.of(pago));
        given(usuarioClient.buscarUsuarioPorId(1L)).willReturn(usuarioDTO);
        given(productoClient.buscarProductoPorId(1L)).willReturn(productoDTO);

        // When
        PagoDTO resultado = pagoService.buscarPorId(idPago);

        // Then
        assertThat(resultado).isNotNull();
        
        verify(pagoRepository, times(1)).findById(idPago);
        verify(usuarioClient, times(1)).buscarUsuarioPorId(1L);
        verify(productoClient, times(1)).buscarProductoPorId(1L);
        verifyNoMoreInteractions(pagoRepository);
    }

    @Test
    void buscarPorId_CuandoPagoNoExiste_DeberiaLanzarPagoNotFoundException() {
        // Given
        Long idPago = 1L;
        given(pagoRepository.findById(idPago)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> pagoService.buscarPorId(idPago))
                .isInstanceOf(PagoNotFoundException.class);
                
        verify(pagoRepository, times(1)).findById(idPago);
        verifyNoInteractions(usuarioClient, productoClient, metodoPagoRepository, estadoPagoRepository);
    }

    @Test
    void registrar_CuandoDatosSonValidosYHayStock_DeberiaGuardarYRetornarPagoDTO() {
        // Given
        PagoRegistroDTO dto = new PagoRegistroDTO();
        dto.setIdUsuario(1L);
        dto.setIdProducto(1L);
        dto.setIdMetodoPago(1L);
        dto.setIdEstadoPago(1L);

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        ProductoTiendaDTO productoDTO = new ProductoTiendaDTO();
        productoDTO.setStock(10); // Stock suficiente
        
        MetodoPago metodoPago = new MetodoPago();
        EstadoPago estadoPago = new EstadoPago();

        given(usuarioClient.buscarUsuarioPorId(1L)).willReturn(usuarioDTO);
        given(productoClient.buscarProductoPorId(1L)).willReturn(productoDTO);
        given(metodoPagoRepository.findById(1L)).willReturn(Optional.of(metodoPago));
        given(estadoPagoRepository.findById(1L)).willReturn(Optional.of(estadoPago));

        Pago pagoGuardado = new Pago();
        pagoGuardado.setIdUsuario(1L);
        pagoGuardado.setIdProducto(1L);
        pagoGuardado.setMetodoPago(metodoPago);
        pagoGuardado.setEstadoPago(estadoPago);
        given(pagoRepository.save(any(Pago.class))).willReturn(pagoGuardado);

        // When
        PagoDTO resultado = pagoService.registrar(dto);

        // Then
        assertThat(resultado).isNotNull();
        
        verify(usuarioClient, times(2)).buscarUsuarioPorId(1L);
        verify(productoClient, times(2)).buscarProductoPorId(1L);
        verify(metodoPagoRepository, times(1)).findById(1L);
        verify(estadoPagoRepository, times(1)).findById(1L);
        verify(pagoRepository, times(1)).save(any(Pago.class));
        verifyNoMoreInteractions(metodoPagoRepository, estadoPagoRepository, pagoRepository);
    }

    @Test
    void registrar_CuandoMetodoPagoNoExiste_DeberiaLanzarMetodoPagoNotFoundException() {
        // Given
        PagoRegistroDTO dto = new PagoRegistroDTO();
        dto.setIdUsuario(1L);
        dto.setIdProducto(1L);
        dto.setIdMetodoPago(99L);

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        ProductoTiendaDTO productoDTO = new ProductoTiendaDTO();

        given(usuarioClient.buscarUsuarioPorId(1L)).willReturn(usuarioDTO);
        given(productoClient.buscarProductoPorId(1L)).willReturn(productoDTO);
        given(metodoPagoRepository.findById(99L)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> pagoService.registrar(dto))
                .isInstanceOf(MetodoPagoNotFoundException.class);
                
        verify(metodoPagoRepository, times(1)).findById(99L);
        verifyNoInteractions(estadoPagoRepository, pagoRepository);
    }

    @Test
    void registrar_CuandoEstadoPagoNoExiste_DeberiaLanzarEstadoPagoNotFoundException() {
        // Given
        PagoRegistroDTO dto = new PagoRegistroDTO();
        dto.setIdUsuario(1L);
        dto.setIdProducto(1L);
        dto.setIdMetodoPago(1L);
        dto.setIdEstadoPago(99L);

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        ProductoTiendaDTO productoDTO = new ProductoTiendaDTO();
        MetodoPago metodoPago = new MetodoPago();

        given(usuarioClient.buscarUsuarioPorId(1L)).willReturn(usuarioDTO);
        given(productoClient.buscarProductoPorId(1L)).willReturn(productoDTO);
        given(metodoPagoRepository.findById(1L)).willReturn(Optional.of(metodoPago));
        given(estadoPagoRepository.findById(99L)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> pagoService.registrar(dto))
                .isInstanceOf(EstadoPagoNotFoundException.class);
                
        verify(estadoPagoRepository, times(1)).findById(99L);
        verifyNoInteractions(pagoRepository);
    }

    @Test
    void registrar_CuandoProductoNoTieneStock_DeberiaLanzarStockInsuficienteException() {
        // Given
        PagoRegistroDTO dto = new PagoRegistroDTO();
        dto.setIdUsuario(1L);
        dto.setIdProducto(1L);
        dto.setIdMetodoPago(1L);
        dto.setIdEstadoPago(1L);

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        ProductoTiendaDTO productoDTO = new ProductoTiendaDTO();
        productoDTO.setStock(0); // Sin stock
        
        MetodoPago metodoPago = new MetodoPago();
        EstadoPago estadoPago = new EstadoPago();

        given(usuarioClient.buscarUsuarioPorId(1L)).willReturn(usuarioDTO);
        given(productoClient.buscarProductoPorId(1L)).willReturn(productoDTO);
        given(metodoPagoRepository.findById(1L)).willReturn(Optional.of(metodoPago));
        given(estadoPagoRepository.findById(1L)).willReturn(Optional.of(estadoPago));

        // When & Then
        assertThatThrownBy(() -> pagoService.registrar(dto))
                .isInstanceOf(StockInsuficienteException.class);
                
        verifyNoInteractions(pagoRepository); // Nunca debería guardar el pago
    }

    @Test
    void generarComprobante_CuandoPagoExiste_DeberiaRetornarComprobanteDTO() {
        // Given
        Long idPago = 1L;
        Pago pago = new Pago();
        pago.setIdPago(idPago);
        pago.setIdUsuario(2L);
        pago.setIdProducto(3L);
        pago.setMonto(100);
        pago.setFechaPago(LocalDateTime.now());
        pago.setCodigoTransaccion("TX-123");
        
        MetodoPago metodoPago = new MetodoPago();
        metodoPago.setNombreMetodo("Tarjeta");
        pago.setMetodoPago(metodoPago);
        
        EstadoPago estadoPago = new EstadoPago();
        estadoPago.setNombreEstado("Aprobado");
        pago.setEstadoPago(estadoPago);

        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setIdUsuario(2L);
        usuario.setNombre("Juan");
        usuario.setApellido("Perez");
        usuario.setCorreo("juan@test.com");
        usuario.setNivelCuenta(5);

        ProductoTiendaDTO producto = new ProductoTiendaDTO();
        producto.setIdProducto(3L);
        producto.setNombreProducto("Espada");
        producto.setDescripcion("Espada magica");
        producto.setNombreCategoria("Armas");

        given(pagoRepository.findById(idPago)).willReturn(Optional.of(pago));
        given(usuarioClient.buscarUsuarioPorId(2L)).willReturn(usuario);
        given(productoClient.buscarProductoPorId(3L)).willReturn(producto);

        // When
        ComprobanteDTO resultado = pagoService.generarComprobante(idPago);

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdPago()).isEqualTo(1L);
        assertThat(resultado.getNombreCliente()).isEqualTo("Juan Perez");
        assertThat(resultado.getNombreProducto()).isEqualTo("Espada");
        
        verify(pagoRepository, times(1)).findById(idPago);
        verify(usuarioClient, times(1)).buscarUsuarioPorId(2L);
        verify(productoClient, times(1)).buscarProductoPorId(3L);
        verifyNoMoreInteractions(pagoRepository, usuarioClient, productoClient);
    }
}
