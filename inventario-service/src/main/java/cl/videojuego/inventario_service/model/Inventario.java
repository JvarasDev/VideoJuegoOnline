package cl.videojuego.inventario_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "inventarios")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInventario;

    private Long idPersonaje;

    private Integer capacidadMaxima;

    private Integer espaciosUsados;

    private LocalDate fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "id_estado_inventario")
    private EstadoInventario estadoInventario;
}