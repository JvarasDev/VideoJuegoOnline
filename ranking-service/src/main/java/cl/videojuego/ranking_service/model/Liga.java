package cl.videojuego.ranking_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ligas")
public class Liga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLiga;

    private String nombreLiga;

    private Integer puntosMinimos;

    private Integer puntosMaximos;

    private String descripcion;
}