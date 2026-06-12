package cl.videojuego.tienda_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estados_producto")
@Getter
@Setter
@NoArgsConstructor
public class EstadoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstadoProducto;

    private String nombreEstado;

    private String descripcion;
}