package cl.videojuego.combate_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "combates")
public class Combate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCombate;

    private Long idPersonajeAtacante;
    private Long idPersonajeDefensor;
    private Long idGanador;

    private LocalDateTime fechaCombate;
    private Integer experienciaGanada;
    private Integer monedasGanadas;
    private Integer duracionSegundos;

    @ManyToOne
    @JoinColumn(name = "id_tipo_combate")
    private TipoCombate tipoCombate;

    @ManyToOne
    @JoinColumn(name = "id_estado_combate")
    private EstadoCombate estadoCombate;
}