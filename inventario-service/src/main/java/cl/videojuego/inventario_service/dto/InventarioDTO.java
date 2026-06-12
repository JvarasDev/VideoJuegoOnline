package cl.videojuego.inventario_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InventarioDTO {

    private Long idInventario;
    private Long idPersonaje;
    private String nombrePersonaje;
    private Integer capacidadMaxima;
    private Integer espaciosUsados;
    private LocalDate fechaCreacion;
    private String nombreEstado;
}