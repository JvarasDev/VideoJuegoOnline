package cl.videojuego.usuario_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagedGlobalResponse<T> {
    private GlobalResponse<T> response;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public static <T> PagedGlobalResponse<T> of(GlobalResponse<T> response, int page, int size, long totalElements, int totalPages) {
        return PagedGlobalResponse.<T>builder()
                .response(response)
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }
}

