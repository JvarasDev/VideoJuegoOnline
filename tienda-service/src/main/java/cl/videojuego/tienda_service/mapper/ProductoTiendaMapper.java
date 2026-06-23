package cl.videojuego.tienda_service.mapper;

import cl.videojuego.tienda_service.dto.ArmaDTO;
import cl.videojuego.tienda_service.dto.ProductoTiendaDTO;
import cl.videojuego.tienda_service.dto.ProductoTiendaRegistroDTO;
import cl.videojuego.tienda_service.model.CategoriaProducto;
import cl.videojuego.tienda_service.model.EstadoProducto;
import cl.videojuego.tienda_service.model.ProductoTienda;
import cl.videojuego.tienda_service.model.TipoItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// componentModel = "spring" permite inyectar el mapper con @Autowired en el service
@Mapper(componentModel = "spring")
public interface ProductoTiendaMapper {

        @Mapping(target = "precio", source = "producto.precio")
        @Mapping(target = "idReferenciaItem", source = "producto.idReferenciaItem")
        @Mapping(target = "nombreItem", source = "arma.nombreArma") // Temporalmente sigue trayendo de arma
        @Mapping(target = "nombreTipoItem", source = "producto.tipoItem.nombreTipo")
        @Mapping(target = "nombreCategoria", source = "producto.categoriaProducto.nombreCategoria")
        @Mapping(target = "nombreEstado", source = "producto.estadoProducto.nombreEstado")
        ProductoTiendaDTO toDTO(ProductoTienda producto, ArmaDTO arma);

        @Mapping(target = "idProducto", ignore = true)
        @Mapping(target = "descripcion", source = "dto.descripcion")
        @Mapping(target = "tipoItem", source = "tipo")
        @Mapping(target = "categoriaProducto", source = "categoria")
        @Mapping(target = "estadoProducto", source = "estado")
        ProductoTienda toEntity(ProductoTiendaRegistroDTO dto, CategoriaProducto categoria, EstadoProducto estado, TipoItem tipo);
}
