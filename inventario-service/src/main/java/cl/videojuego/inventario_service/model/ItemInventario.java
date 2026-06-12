package cl.videojuego.inventario_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "items_inventario")
public class ItemInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItemInventario;

    @ManyToOne
    @JoinColumn(name = "id_inventario")
    private Inventario inventario;

    private Long idReferenciaItem;

    @ManyToOne
    @JoinColumn(name = "id_tipo_item")
    private TipoItem tipoItem;

    private Integer cantidad;

    private LocalDate fechaObtencion;

    private Boolean equipado;
}