package cl.videojuego.pago_service.service;

import cl.videojuego.pago_service.client.ProductoClient;
import cl.videojuego.pago_service.client.UsuarioClient;
import cl.videojuego.pago_service.dto.*;
import cl.videojuego.pago_service.exception.PagoNotFoundException;
import cl.videojuego.pago_service.exception.StockInsuficienteException;
import cl.videojuego.pago_service.exception.MetodoPagoNotFoundException;
import cl.videojuego.pago_service.exception.EstadoPagoNotFoundException;
import cl.videojuego.pago_service.mapper.PagoMapper;
import cl.videojuego.pago_service.model.Pago;
import cl.videojuego.pago_service.repository.EstadoPagoRepository;
import cl.videojuego.pago_service.repository.MetodoPagoRepository;
import cl.videojuego.pago_service.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class PagoService {

    private final PagoRepository pagoRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final EstadoPagoRepository estadoPagoRepository;
    private final UsuarioClient usuarioClient;
    private final ProductoClient productoClient;

    // Helpers privados

    private PagoDTO resolverDTO(Pago pago) {
        UsuarioDTO usuario = usuarioClient.buscarUsuarioPorId(pago.getIdUsuario());
        ProductoTiendaDTO producto = productoClient.buscarProductoPorId(pago.getIdProducto());
        return PagoMapper.toDTO(pago, usuario, producto);
    }

    // Metodos publicos

    public List<PagoDTO> listarTodos() {
        return pagoRepository.findAll()
                .stream()
                .map(this::resolverDTO)
                .toList();
    }

    public List<PagoDTO> listarPorUsuario(Long idUsuario) {
        return pagoRepository.findByIdUsuario(idUsuario)
                .stream()
                .map(this::resolverDTO)
                .toList();
    }

    
    public List<PagoDTO> listarPorEstado(Long idEstadoPago) {
        return pagoRepository.findByEstadoPago_IdEstadoPago(idEstadoPago)
                .stream()
                .map(this::resolverDTO)
                .toList();
    }

    public PagoDTO buscarPorId(Long idPago) {
        return pagoRepository.findById(idPago)
                .map(this::resolverDTO)
                .orElseThrow(() -> new PagoNotFoundException(idPago));
    }

    public PagoDTO registrar(PagoRegistroDTO dto) {
        usuarioClient.buscarUsuarioPorId(dto.getIdUsuario());
        ProductoTiendaDTO producto = productoClient.buscarProductoPorId(dto.getIdProducto());

        var metodoPago = metodoPagoRepository.findById(dto.getIdMetodoPago())
                .orElseThrow(() -> new MetodoPagoNotFoundException(dto.getIdMetodoPago()));

        var estadoPago = estadoPagoRepository.findById(dto.getIdEstadoPago())
                .orElseThrow(() -> new EstadoPagoNotFoundException(dto.getIdEstadoPago()));

        if (producto.getStock() <= 0) {
            throw new StockInsuficienteException(dto.getIdProducto());

        }

        Pago pago = new Pago();
        pago.setIdUsuario(dto.getIdUsuario());
        pago.setIdProducto(dto.getIdProducto());
        pago.setMonto(dto.getMonto());
        pago.setFechaPago(LocalDateTime.now());
        pago.setCodigoTransaccion(UUID.randomUUID().toString());
        pago.setMetodoPago(metodoPago);
        pago.setEstadoPago(estadoPago);

        return resolverDTO(pagoRepository.save(pago));
    }

    public ComprobanteDTO generarComprobante(Long idPago) {
        Pago pago = pagoRepository.findById(idPago)
                .orElseThrow(() -> new PagoNotFoundException(idPago));

        UsuarioDTO usuario = usuarioClient.buscarUsuarioPorId(pago.getIdUsuario());
        ProductoTiendaDTO producto = productoClient.buscarProductoPorId(pago.getIdProducto());

        return ComprobanteDTO.builder()
                .idPago(pago.getIdPago())
                .montoPagado(pago.getMonto())
                .fechaPago(pago.getFechaPago())
                .codigoTransaccion(pago.getCodigoTransaccion())
                .metodoPago(pago.getMetodoPago().getNombreMetodo())
                .estadoPago(pago.getEstadoPago().getNombreEstado())
                .idUsuario(usuario.getIdUsuario())
                .nombreCliente(usuario.getNombre() + " " + usuario.getApellido())
                .correoCliente(usuario.getCorreo())
                .nivelCuenta(usuario.getNivelCuenta())
                .idProducto(producto.getIdProducto())
                .nombreProducto(producto.getNombreProducto())
                .descripcionProducto(producto.getDescripcion())
                .categoriaProducto(producto.getNombreCategoria())
                
                .build();
    }
}