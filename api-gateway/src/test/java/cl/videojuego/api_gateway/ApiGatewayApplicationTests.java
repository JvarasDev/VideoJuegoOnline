package cl.videojuego.api_gateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Verifica que el contexto de Spring se carga correctamente.
 * Comprueba que todas las rutas y filtros del gateway están bien configurados.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiGatewayApplicationTests {

    @Test
    void contextLoads() {
        // El contexto debe iniciarse sin errores de configuración
    }
}

