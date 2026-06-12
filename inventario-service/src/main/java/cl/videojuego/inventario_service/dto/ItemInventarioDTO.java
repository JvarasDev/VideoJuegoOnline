package cl.videojuego.inventario_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ItemInventarioDTO {

    private Long idItemInventario;
    private Long idInventario;
    private Long idReferenciaItem;
    private String nombreItem;
    private String nombreTipoItem;
    private Integer cantidad;
    private LocalDate fechaObtencion;
    private Boolean equipado;
}