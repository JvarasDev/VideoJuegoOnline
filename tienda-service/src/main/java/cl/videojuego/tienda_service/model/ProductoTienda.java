
package cl.videojuego.tienda_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "productos_tienda")
@Getter
@Setter
@NoArgsConstructor
public class ProductoTienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    private String nombreProducto;

    private String descripcion;

    private Integer precio;

    private Integer stock;

    private Long idReferenciaItem;

    @ManyToOne
    @JoinColumn(name = "id_tipo_item")
    private TipoItem tipoItem;

    @ManyToOne
    @JoinColumn(name = "id_categoria_producto")
    private CategoriaProducto categoriaProducto;

    @ManyToOne
    @JoinColumn(name = "id_estado_producto")
    private EstadoProducto estadoProducto;
}