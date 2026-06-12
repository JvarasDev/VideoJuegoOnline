package cl.videojuego.inventario_service.mapper;

import cl.videojuego.inventario_service.dto.InventarioDTO;
import cl.videojuego.inventario_service.dto.PersonajeDTO;
import cl.videojuego.inventario_service.model.Inventario;

public class InventarioMapper {

    public static InventarioDTO toDTO(
            Inventario inventario,
            PersonajeDTO personaje
    ) {

        InventarioDTO dto = new InventarioDTO();

        dto.setIdInventario(
                inventario.getIdInventario()
        );

        dto.setIdPersonaje(
                inventario.getIdPersonaje()
        );

        dto.setNombrePersonaje(
                personaje.getNombre()
        );

        dto.setCapacidadMaxima(
                inventario.getCapacidadMaxima()
        );

        dto.setEspaciosUsados(
                inventario.getEspaciosUsados()
        );

        dto.setFechaCreacion(
                inventario.getFechaCreacion()
        );

        dto.setNombreEstado(
                inventario.getEstadoInventario()
                        .getNombreEstado()
        );

        return dto;
    }
}