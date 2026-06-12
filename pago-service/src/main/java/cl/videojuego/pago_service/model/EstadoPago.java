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
@Table(name = "estados_pago")
public class EstadoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstadoPago;

    private String nombreEstado;

    private String descripcion;



}