package cl.videojuego.combate_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "estados_combate")
public class EstadoCombate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstadoCombate;

    private String nombreEstado;
    private String descripcion;
}