package cl.videojuego.pago_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor // genera el constructor con todos los atributos como parámetros
@NoArgsConstructor  // genera el constructor vacío sin parámetros
@Table(name = "metodos_pago")
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMetodoPago;

    private String nombreMetodo;

    private String descripcion;

}
