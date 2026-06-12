package cl.videojuego.pago_service.dto;

import lombok.Data;

@Data
public class UsuarioDTO {

    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private Integer nivelCuenta;
    private String nombreRol;
    private String nombreEstado;
}