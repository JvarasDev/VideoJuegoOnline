package cl.videojuego.personaje_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "personajes")
public class Personaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersonaje;

    private String nombre;

    private Integer nivel;

    private Integer vida;

    private Integer mana;

    private Long idUsuario;

    @ManyToOne
    @JoinColumn(name = "id_clase_personaje")
    private ClasePersonaje clasePersonaje;

    @ManyToOne
    @JoinColumn(name = "id_estado_personaje")
    private EstadoPersonaje estadoPersonaje;
}
