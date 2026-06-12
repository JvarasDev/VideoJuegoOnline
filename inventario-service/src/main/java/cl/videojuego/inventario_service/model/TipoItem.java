package cl.videojuego.inventario_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tipos_item")
@Getter
@Setter
@NoArgsConstructor
public class TipoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoItem;

    @Column(nullable = false, unique = true)
    private String nombreTipo;
}
