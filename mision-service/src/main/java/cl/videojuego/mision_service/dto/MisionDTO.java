package cl.videojuego.mision_service.dto;

import lombok.Data;

// DTO que se devuelve como respuesta en Postman
// No devuelve objetos completos, solo datos importantes
@Data
public class MisionDTO {

    private Long idMision;

    private String nombreMision;

    private String descripcion;

    private Integer recompensaExperiencia;

    private Integer recompensaMonedas;

    private Integer nivelMinimo;

    private String nombreTipo;

    private String nombreEstado;
}