package cl.videojuego.pago_service.service;


import cl.videojuego.pago_service.client.ProductoClient;
import cl.videojuego.pago_service.client.UsuarioClient;
import cl.videojuego.pago_service.dto.PagoRegistroDTO;
import cl.videojuego.pago_service.dto.ProductoTiendaDTO;
import cl.videojuego.pago_service.dto.UsuarioDTO;
import cl.videojuego.pago_service.model.EstadoPago;
import cl.videojuego.pago_service.model.MetodoPago;
import cl.videojuego.pago_service.repository.EstadoPagoRepository;
import cl.videojuego.pago_service.repository.MetodoPagoRepository;
import cl.videojuego.pago_service.repository.PagoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.Optional;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class serviceTest {
    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private MetodoPagoRepository metodoPagoRepository;

    @Mock
    private EstadoPagoRepository estadoPagoRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private ProductoClient  productoClient;


    @InjectMocks
    private PagoService pagoService;

    @Test
    void registrar_whendatoValidos_shouldGuardarPago(){
        //given
        PagoRegistroDTO dto = new PagoRegistroDTO();
        dto.setIdUsuario(1L);
        dto.setIdProducto(10L);
        dto.setIdMetodoPago(2L);
        dto.setIdEstadoPago(3L);
        dto.setMonto(15000);

        UsuarioDTO usuario = new UsuarioDTO();
        ProductoTiendaDTO producto = new ProductoTiendaDTO();
        producto.setStock(5);
        MetodoPago metodoPago = new MetodoPago();
        EstadoPago estadoPago = new EstadoPago();
        when(usuarioClient.buscarUsuarioPorId(1L));
        when(productoClient.buscarProductoPorId(10L)).thenReturn(producto);
        when(metodoPagoRepository.findById(1L)).thenReturn(Optional.of(metodoPago));
        when(estadoPagoRepository.findById(1L)).thenReturn(Optional.of(estadoPago));



    }





















}


