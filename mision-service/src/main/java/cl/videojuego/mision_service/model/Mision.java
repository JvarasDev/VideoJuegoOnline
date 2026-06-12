package cl.videojuego.mision_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "misiones")
public class Mision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMision;

    private String nombreMision;

    private String descripcion;

    private Integer recompensaExperiencia;

    private Integer recompensaMonedas;

    private Integer nivelMinimo;

    @ManyToOne
    @JoinColumn(name = "id_tipo_mision")
    private TipoMision tipoMision;

    @ManyToOne
    @JoinColumn(name = "id_estado_mision")
    private EstadoMision estadoMision;
}