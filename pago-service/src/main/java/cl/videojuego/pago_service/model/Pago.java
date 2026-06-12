package cl.videojuego.pago_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor // genera el constructor con todos los atributos como parámetros
@NoArgsConstructor  // genera el constructor vacío sin parámetros
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    private Long idUsuario;

    private Long idProducto;

    private Integer monto;

    private LocalDateTime fechaPago;

    private String codigoTransaccion;

    @ManyToOne
    @JoinColumn(name = "id_metodo_pago")
    private MetodoPago metodoPago;

    @ManyToOne
    @JoinColumn(name = "id_estado_pago")
    private EstadoPago estadoPago;


}