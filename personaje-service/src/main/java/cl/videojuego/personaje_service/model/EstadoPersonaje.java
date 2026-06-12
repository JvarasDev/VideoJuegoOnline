package cl.videojuego.personaje_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "estados_personaje")
public class EstadoPersonaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstadoPersonaje;

    private String nombreEstado;
}
