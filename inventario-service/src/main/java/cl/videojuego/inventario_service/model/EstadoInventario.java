package cl.videojuego.inventario_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "estados_inventario")
public class EstadoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstadoInventario;

    private String nombreEstado;

    private String descripcion;
}