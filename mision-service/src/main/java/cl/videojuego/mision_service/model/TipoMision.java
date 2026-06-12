package cl.videojuego.mision_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tipos_mision")
public class TipoMision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoMision;

    private String nombreTipo;

    private String descripcion;
}