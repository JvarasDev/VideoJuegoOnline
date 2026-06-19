package cl.videojuego.pago_service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import cl.videojuego.pago_service.client.ProductoClient;
import cl.videojuego.pago_service.client.UsuarioClient;
import cl.videojuego.pago_service.dto.*;
import cl.videojuego.pago_service.model.EstadoPago;
import cl.videojuego.pago_service.model.MetodoPago;
import cl.videojuego.pago_service.model.Pago;
import cl.videojuego.pago_service.repository.EstadoPagoRepository;
import cl.videojuego.pago_service.repository.MetodoPagoRepository;
import cl.videojuego.pago_service.repository.PagoRepository;
import cl.videojuego.pago_service.service.PagoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias capa Service")
public class PagoService_Test {

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

    private Pago pagoPrueba;
    private MetodoPago metodoPagoPrueba;
    private EstadoPago estadoPagoPrueba;
    private UsuarioDTO usuarioPrueba;
    private ProductoTiendaDTO productoPrueba;
    private PagoRegistroDTO pagoRegistroPrueba;

    @BeforeEach
    void setUp(){
        // Inicializamos los datos para las pruebas correspondientes a usuarioDto
        usuarioPrueba = new UsuarioDTO();
        usuarioPrueba.setIdUsuario(1L);
        usuarioPrueba.setNombre("Juan");
        usuarioPrueba.setApellido("Varas");
        usuarioPrueba.setCorreo("ju.varasc@duocuc.cl");
        usuarioPrueba.setNivelCuenta(1000);
        usuarioPrueba.setNombreRol("ADMIN");
        usuarioPrueba.setNombreEstado("ACTIVO");

        // Inicializamos los datos para las pruebas correspondientes a productoTiendaDto
        productoPrueba = new ProductoTiendaDTO();
        productoPrueba.setIdProducto(1L);
        productoPrueba.setNombreProducto("Espada");
        productoPrueba.setDescripcion("Espada");
        productoPrueba.setPrecio(1000);
        productoPrueba.setStock(1);
        productoPrueba.setIdArma(1L);
        productoPrueba.setNombreArma("Espada");
        productoPrueba.setNombreCategoria("Espada");
        productoPrueba.setNombreEstado("ACTIVO");
        
        metodoPagoPrueba = new MetodoPago();
        metodoPagoPrueba.setIdMetodoPago(1L);
        metodoPagoPrueba.setNombreMetodo("Tarjeta");
        metodoPagoPrueba.setDescripcion("Tarjeta");

        estadoPagoPrueba = new EstadoPago();
        estadoPagoPrueba.setIdEstadoPago(1L);
        estadoPagoPrueba.setNombreEstado("ACTIVO");
        estadoPagoPrueba.setDescripcion("ACTIVO");

        // --- Nuevos agregados ---
        pagoRegistroPrueba = new PagoRegistroDTO();
        pagoRegistroPrueba.setIdUsuario(1L);
        pagoRegistroPrueba.setIdProducto(1L);
        pagoRegistroPrueba.setMonto(1000); 
        pagoRegistroPrueba.setIdMetodoPago(1L);
        pagoRegistroPrueba.setIdEstadoPago(1L);

        pagoPrueba = new Pago();
        pagoPrueba.setIdPago(100L); 
        pagoPrueba.setIdUsuario(1L);
        pagoPrueba.setIdProducto(1L);
        pagoPrueba.setMonto(1000);
        pagoPrueba.setCodigoTransaccion(UUID.randomUUID().toString());
        pagoPrueba.setMetodoPago(metodoPagoPrueba);
        pagoPrueba.setEstadoPago(estadoPagoPrueba);
    }   

    @Test
    @DisplayName("Debe registrar un pago correctamente cuando hay stock y los datos existen")
    void registrarPago_CaminoFeliz_DeberiaRetornarPagoDTO() {
        
        // 1. ARRANGE (Preparar)
        when(usuarioClient.buscarUsuarioPorId(1L)).thenReturn(usuarioPrueba);
        when(productoClient.buscarProductoPorId(1L)).thenReturn(productoPrueba);
        
        when(metodoPagoRepository.findById(1L)).thenReturn(Optional.of(metodoPagoPrueba));
        when(estadoPagoRepository.findById(1L)).thenReturn(Optional.of(estadoPagoPrueba));
        
        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoPrueba);

        // 2. ACT (Actuar)
        PagoDTO resultado = pagoService.registrar(pagoRegistroPrueba);

        // 3. ASSERT (Afirmar)
        assertNotNull(resultado, "El resultado no debería ser nulo");
        assertEquals(1000, resultado.getMonto(), "El monto debe coincidir");
        assertEquals("ju.varasc@duocuc.cl", resultado.getCorreoUsuario(), "El correo del usuario debe coincidir");
        assertEquals("Espada", resultado.getNombreProducto(), "El nombre del producto debe coincidir");
        assertEquals("Tarjeta", resultado.getNombreMetodo(), "El método de pago debe coincidir");
        
        // Verificar que se guardó en BD exactamente 1 vez
        verify(pagoRepository, times(1)).save(any(Pago.class));
    }
}
