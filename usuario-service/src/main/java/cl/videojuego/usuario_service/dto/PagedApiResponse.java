package cl.videojuego.usuario_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagedApiResponse<T> {
    private ApiResponsev1<T> response;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public static <T> PagedApiResponse<T> of(ApiResponsev1<T> response, int page, int size, long totalElements, int totalPages) {
        return PagedApiResponse.<T>builder()
                .response(response)
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }
}
