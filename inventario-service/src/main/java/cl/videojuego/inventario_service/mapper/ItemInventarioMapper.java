package cl.videojuego.inventario_service.mapper;

import cl.videojuego.inventario_service.dto.ArmaDTO;
import cl.videojuego.inventario_service.dto.ItemInventarioDTO;
import cl.videojuego.inventario_service.model.ItemInventario;

public class ItemInventarioMapper {

    public static ItemInventarioDTO toDTO(
            ItemInventario item,
            ArmaDTO arma
    ) {

        ItemInventarioDTO dto =
                new ItemInventarioDTO();

        dto.setIdItemInventario(
                item.getIdItemInventario()
        );

        dto.setIdInventario(
                item.getInventario()
                        .getIdInventario()
        );

        dto.setIdReferenciaItem(
                item.getIdReferenciaItem()
        );

        dto.setNombreItem(
                arma.getNombreArma() // TODO: Traer dinámicamente si no es arma
        );
        
        dto.setNombreTipoItem(
                item.getTipoItem() != null ? item.getTipoItem().getNombreTipo() : null
        );

        dto.setCantidad(
                item.getCantidad()
        );

        dto.setFechaObtencion(
                item.getFechaObtencion()
        );

        dto.setEquipado(
                item.getEquipado()
        );

        return dto;
    }
}