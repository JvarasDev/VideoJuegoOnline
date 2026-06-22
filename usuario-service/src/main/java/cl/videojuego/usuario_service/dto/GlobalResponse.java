package cl.videojuego.usuario_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
<<<<<<<< HEAD:usuario-service/src/main/java/cl/videojuego/usuario_service/dto/ApiResponsev1.java
public class ApiResponsev1<T> {
========
public class GlobalResponse<T> {
>>>>>>>> a7c43fd2c70aa9e87c829e7285155016252c9d2f:usuario-service/src/main/java/cl/videojuego/usuario_service/dto/GlobalResponse.java
    private int status;
    private String message;
    private T data;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

<<<<<<<< HEAD:usuario-service/src/main/java/cl/videojuego/usuario_service/dto/ApiResponsev1.java
    public static <T> ApiResponsev1<T> success(T data, String message) {
        return ApiResponsev1.<T>builder()
========
    public static <T> GlobalResponse<T> success(T data, String message) {
        return GlobalResponse.<T>builder()
>>>>>>>> a7c43fd2c70aa9e87c829e7285155016252c9d2f:usuario-service/src/main/java/cl/videojuego/usuario_service/dto/GlobalResponse.java
                .status(200)
                .message(message)
                .data(data)
                .build();
    }

<<<<<<<< HEAD:usuario-service/src/main/java/cl/videojuego/usuario_service/dto/ApiResponsev1.java
    public static <T> ApiResponsev1<T> error(int status, String message) {
        return ApiResponsev1.<T>builder()
========
    public static <T> GlobalResponse<T> error(int status, String message) {
        return GlobalResponse.<T>builder()
>>>>>>>> a7c43fd2c70aa9e87c829e7285155016252c9d2f:usuario-service/src/main/java/cl/videojuego/usuario_service/dto/GlobalResponse.java
                .status(status)
                .message(message)
                .build();
    }
}

