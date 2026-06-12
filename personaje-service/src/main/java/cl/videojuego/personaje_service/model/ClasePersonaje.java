package cl.videojuego.personaje_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "clases_personaje")
public class ClasePersonaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClasePersonaje;

    private String nombreClase;

    private String descripcion;
}
