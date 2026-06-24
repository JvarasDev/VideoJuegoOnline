package cl.videojuego.pago_service.client;

import cl.videojuego.pago_service.dto.ProductoTiendaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "api-gateway", contextId = "productoClient")
public interface ProductoClient {

    @GetMapping("/api/productos/{idProducto}")
    ProductoTiendaDTO buscarProductoPorId(@PathVariable Long idProducto);
}