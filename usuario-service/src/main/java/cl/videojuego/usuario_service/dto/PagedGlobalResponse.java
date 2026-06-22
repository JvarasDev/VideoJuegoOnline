package cl.videojuego.usuario_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
<<<<<<< HEAD:usuario-service/src/main/java/cl/videojuego/usuario_service/dto/PagedApiResponse.java
public class PagedApiResponse<T> {
    private ApiResponsev1<T> response;
=======
public class PagedGlobalResponse<T> {
    private GlobalResponse<T> response;
>>>>>>> a7c43fd2c70aa9e87c829e7285155016252c9d2f:usuario-service/src/main/java/cl/videojuego/usuario_service/dto/PagedGlobalResponse.java
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

<<<<<<< HEAD:usuario-service/src/main/java/cl/videojuego/usuario_service/dto/PagedApiResponse.java
    public static <T> PagedApiResponse<T> of(ApiResponsev1<T> response, int page, int size, long totalElements, int totalPages) {
        return PagedApiResponse.<T>builder()
=======
    public static <T> PagedGlobalResponse<T> of(GlobalResponse<T> response, int page, int size, long totalElements, int totalPages) {
        return PagedGlobalResponse.<T>builder()
>>>>>>> a7c43fd2c70aa9e87c829e7285155016252c9d2f:usuario-service/src/main/java/cl/videojuego/usuario_service/dto/PagedGlobalResponse.java
                .response(response)
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }
}

