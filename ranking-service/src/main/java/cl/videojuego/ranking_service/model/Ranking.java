package cl.videojuego.ranking_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "rankings")
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRanking;

    private Long idPersonaje;

    private Integer puntos;

    private Integer victorias;

    private Integer derrotas;

    private Integer posicion;

    @ManyToOne
    @JoinColumn(name = "id_temporada")
    private Temporada temporada;

    @ManyToOne
    @JoinColumn(name = "id_liga")
    private Liga liga;
}