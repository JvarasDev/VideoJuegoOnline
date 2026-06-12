package cl.videojuego.pago_service.exception;

import org.springframework.http.HttpStatus;

public class StockInsuficienteException extends ApiException {
    public StockInsuficienteException(Long idProducto) {
        super("Sin stock disponible para el producto ID: " + idProducto, HttpStatus.CONFLICT, "STOCK_INSUFICIENTE");
    }
}
