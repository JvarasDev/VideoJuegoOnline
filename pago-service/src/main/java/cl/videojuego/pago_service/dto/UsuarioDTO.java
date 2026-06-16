package cl.videojuego.pago_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@JsonFormat
public class UsuarioDTO {

    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private Integer nivelCuenta;
    private String nombreRol;
    private String nombreEstado;
}