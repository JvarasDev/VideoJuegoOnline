package cl.videojuego.usuario_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estados_usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstadoUsuario;

    @Size(min = 3, max = 50)
    @Column(name = "nombre_estado_usuario", nullable = false, unique = true, length = 50)
    private String nombreEstadoUsuario;
}
