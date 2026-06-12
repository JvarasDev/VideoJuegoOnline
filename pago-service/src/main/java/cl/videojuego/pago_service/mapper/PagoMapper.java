package cl.videojuego.pago_service.mapper;

import cl.videojuego.pago_service.dto.PagoDTO;
import cl.videojuego.pago_service.dto.ProductoTiendaDTO;
import cl.videojuego.pago_service.dto.UsuarioDTO;
import cl.videojuego.pago_service.model.Pago;

public class PagoMapper {

    public static PagoDTO toDTO(Pago pago, UsuarioDTO usuario, ProductoTiendaDTO producto) {
        return PagoDTO.builder()
                .idPago(pago.getIdPago())
                .idUsuario(pago.getIdUsuario())
                .correoUsuario(usuario.getCorreo())
                .idProducto(pago.getIdProducto())
                .nombreProducto(producto.getNombreProducto())
                .monto(pago.getMonto())
                .fechaPago(pago.getFechaPago())
                .codigoTransaccion(pago.getCodigoTransaccion())
                .nombreMetodo(pago.getMetodoPago().getNombreMetodo())
                .nombreEstado(pago.getEstadoPago().getNombreEstado())
                .build();





    }

}