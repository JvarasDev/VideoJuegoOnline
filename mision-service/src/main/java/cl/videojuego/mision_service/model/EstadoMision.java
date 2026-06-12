package cl.videojuego.mision_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "estados_mision")
public class EstadoMision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstadoMision;

    private String nombreEstado;

    private String descripcion;
}