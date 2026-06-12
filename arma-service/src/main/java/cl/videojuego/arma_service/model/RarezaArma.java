package cl.videojuego.arma_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "rarezas_arma")
public class RarezaArma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRarezaArma;

    private String nombreRareza;

    private Double multiplicadorDanio;

    private String descripcion;
}
