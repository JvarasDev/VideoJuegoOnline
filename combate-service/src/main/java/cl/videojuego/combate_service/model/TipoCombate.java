package cl.videojuego.combate_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tipos_combate")
public class TipoCombate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoCombate;

    private String nombreTipo;
    private String descripcion;
    private Integer recompensaBase;
}