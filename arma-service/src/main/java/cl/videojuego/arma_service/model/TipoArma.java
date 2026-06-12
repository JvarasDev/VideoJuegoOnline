package cl.videojuego.arma_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Indica que esta clase será una tabla en MySQL
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tipos_arma")
public class TipoArma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoArma;

    private String nombreTipo;

    private String descripcion;
}
