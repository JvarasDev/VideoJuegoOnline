package cl.videojuego.arma_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Esta clase representa la tabla armas
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "armas")
public class Arma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArma;

    private String nombreArma;

    private Integer danio;

    private Integer nivelMinimo;

    private Integer precio;

    // Muchas armas pueden tener el mismo tipo, por ejemplo muchas espadas
    @ManyToOne
    @JoinColumn(name = "id_tipo_arma")
    private TipoArma tipoArma;

    // Muchas armas pueden tener la misma rareza, por ejemplo varias armas épicas
    @ManyToOne
    @JoinColumn(name = "id_rareza_arma")
    private RarezaArma rarezaArma;
}
