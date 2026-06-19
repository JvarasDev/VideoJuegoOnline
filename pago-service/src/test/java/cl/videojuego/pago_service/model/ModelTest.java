package cl.videojuego.pago_service.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para el paquete cl.videojuego.pago_service.model.
 * Cubre al 100%: MetodoPago, EstadoPago y Pago.
 * No requiere Spring context ni base de datos.
 */
@DisplayName("Tests del paquete Model")
class ModelTest {

    // =========================================================================
    // MetodoPago
    // =========================================================================

    @Nested
    @DisplayName("MetodoPago")
    class MetodoPagoTest {

        @Test
        @DisplayName("Constructor vacio debe crear objeto con valores nulos")
        void constructorVacio_DebeCrearObjetoConValoresNulos() {
            MetodoPago metodo = new MetodoPago();

            assertNull(metodo.getIdMetodoPago(),  "idMetodoPago debe ser nulo");
            assertNull(metodo.getNombreMetodo(),  "nombreMetodo debe ser nulo");
            assertNull(metodo.getDescripcion(),   "descripcion debe ser nulo");
        }

        @Test
        @DisplayName("Constructor completo debe asignar todos los campos")
        void constructorCompleto_DebeAsignarTodosLosCampos() {
            MetodoPago metodo = new MetodoPago(1L, "Tarjeta", "Pago con tarjeta de credito");

            assertEquals(1L,                          metodo.getIdMetodoPago());
            assertEquals("Tarjeta",                   metodo.getNombreMetodo());
            assertEquals("Pago con tarjeta de credito", metodo.getDescripcion());
        }

        @Test
        @DisplayName("Setters deben modificar los campos correctamente")
        void setters_DebemModificarCampos() {
            MetodoPago metodo = new MetodoPago();
            metodo.setIdMetodoPago(2L);
            metodo.setNombreMetodo("Transferencia");
            metodo.setDescripcion("Pago via transferencia bancaria");

            assertEquals(2L,                             metodo.getIdMetodoPago());
            assertEquals("Transferencia",                metodo.getNombreMetodo());
            assertEquals("Pago via transferencia bancaria", metodo.getDescripcion());
        }

        @Test
        @DisplayName("Setters deben aceptar valores nulos")
        void setters_DebemAceptarValoresNulos() {
            MetodoPago metodo = new MetodoPago(1L, "Tarjeta", "Descripcion");
            metodo.setIdMetodoPago(null);
            metodo.setNombreMetodo(null);
            metodo.setDescripcion(null);

            assertNull(metodo.getIdMetodoPago());
            assertNull(metodo.getNombreMetodo());
            assertNull(metodo.getDescripcion());
        }

        @Test
        @DisplayName("Dos instancias con mismos datos deben ser independientes")
        void dosInstancias_DebenSerIndependientes() {
            MetodoPago a = new MetodoPago(1L, "Efectivo", "Pago en efectivo");
            MetodoPago b = new MetodoPago(1L, "Efectivo", "Pago en efectivo");

            assertNotSame(a, b, "Deben ser objetos distintos en memoria");
            assertEquals(a.getIdMetodoPago(), b.getIdMetodoPago());
            assertEquals(a.getNombreMetodo(), b.getNombreMetodo());
            assertEquals(a.getDescripcion(),  b.getDescripcion());
        }
    }

    // =========================================================================
    // EstadoPago
    // =========================================================================

    @Nested
    @DisplayName("EstadoPago")
    class EstadoPagoTest {

        @Test
        @DisplayName("Constructor vacio debe crear objeto con valores nulos")
        void constructorVacio_DebeCrearObjetoConValoresNulos() {
            EstadoPago estado = new EstadoPago();

            assertNull(estado.getIdEstadoPago(), "idEstadoPago debe ser nulo");
            assertNull(estado.getNombreEstado(), "nombreEstado debe ser nulo");
            assertNull(estado.getDescripcion(),  "descripcion debe ser nulo");
        }

        @Test
        @DisplayName("Constructor completo debe asignar todos los campos")
        void constructorCompleto_DebeAsignarTodosLosCampos() {
            EstadoPago estado = new EstadoPago(1L, "PENDIENTE", "Pago pendiente de confirmacion");

            assertEquals(1L,                               estado.getIdEstadoPago());
            assertEquals("PENDIENTE",                      estado.getNombreEstado());
            assertEquals("Pago pendiente de confirmacion", estado.getDescripcion());
        }

        @Test
        @DisplayName("Setters deben modificar los campos correctamente")
        void setters_DebemModificarCampos() {
            EstadoPago estado = new EstadoPago();
            estado.setIdEstadoPago(3L);
            estado.setNombreEstado("COMPLETADO");
            estado.setDescripcion("Pago completado exitosamente");

            assertEquals(3L,                             estado.getIdEstadoPago());
            assertEquals("COMPLETADO",                   estado.getNombreEstado());
            assertEquals("Pago completado exitosamente", estado.getDescripcion());
        }

        @Test
        @DisplayName("Setters deben aceptar valores nulos")
        void setters_DebemAceptarValoresNulos() {
            EstadoPago estado = new EstadoPago(1L, "ACTIVO", "Descripcion");
            estado.setIdEstadoPago(null);
            estado.setNombreEstado(null);
            estado.setDescripcion(null);

            assertNull(estado.getIdEstadoPago());
            assertNull(estado.getNombreEstado());
            assertNull(estado.getDescripcion());
        }

        @Test
        @DisplayName("Cambio de estado debe reflejarse inmediatamente via getter")
        void cambioDeEstado_DebeReflejarsePorGetter() {
            EstadoPago estado = new EstadoPago(1L, "PENDIENTE", "En espera");
            assertEquals("PENDIENTE", estado.getNombreEstado());

            estado.setNombreEstado("APROBADO");
            assertEquals("APROBADO", estado.getNombreEstado());

            estado.setNombreEstado("RECHAZADO");
            assertEquals("RECHAZADO", estado.getNombreEstado());
        }
    }

    // =========================================================================
    // Pago
    // =========================================================================

    @Nested
    @DisplayName("Pago")
    class PagoTest {

        private MetodoPago metodoPago() {
            return new MetodoPago(1L, "Tarjeta", "Pago con tarjeta");
        }

        private EstadoPago estadoPago() {
            return new EstadoPago(1L, "APROBADO", "Pago aprobado");
        }

        @Test
        @DisplayName("Constructor vacio debe crear objeto con todos los campos nulos")
        void constructorVacio_DebeCrearObjetoConValoresNulos() {
            Pago pago = new Pago();

            assertNull(pago.getIdPago());
            assertNull(pago.getIdUsuario());
            assertNull(pago.getIdProducto());
            assertNull(pago.getMonto());
            assertNull(pago.getFechaPago());
            assertNull(pago.getCodigoTransaccion());
            assertNull(pago.getMetodoPago());
            assertNull(pago.getEstadoPago());
        }

        @Test
        @DisplayName("Constructor completo debe asignar todos los campos")
        void constructorCompleto_DebeAsignarTodosLosCampos() {
            LocalDateTime ahora     = LocalDateTime.now();
            MetodoPago   metodo     = metodoPago();
            EstadoPago   estado     = estadoPago();

            Pago pago = new Pago(100L, 1L, 2L, 5000, ahora, "TXN-ABC-123", metodo, estado);

            assertEquals(100L,          pago.getIdPago());
            assertEquals(1L,            pago.getIdUsuario());
            assertEquals(2L,            pago.getIdProducto());
            assertEquals(5000,          pago.getMonto());
            assertEquals(ahora,         pago.getFechaPago());
            assertEquals("TXN-ABC-123", pago.getCodigoTransaccion());
            assertSame(metodo,          pago.getMetodoPago());
            assertSame(estado,          pago.getEstadoPago());
        }

        @Test
        @DisplayName("Setters deben modificar cada campo correctamente")
        void setters_DebemModificarCadaCampo() {
            LocalDateTime fecha  = LocalDateTime.of(2025, 6, 19, 10, 30);
            MetodoPago   metodo  = metodoPago();
            EstadoPago   estado  = estadoPago();

            Pago pago = new Pago();
            pago.setIdPago(50L);
            pago.setIdUsuario(7L);
            pago.setIdProducto(3L);
            pago.setMonto(12000);
            pago.setFechaPago(fecha);
            pago.setCodigoTransaccion("TXN-XYZ-999");
            pago.setMetodoPago(metodo);
            pago.setEstadoPago(estado);

            assertEquals(50L,           pago.getIdPago());
            assertEquals(7L,            pago.getIdUsuario());
            assertEquals(3L,            pago.getIdProducto());
            assertEquals(12000,         pago.getMonto());
            assertEquals(fecha,         pago.getFechaPago());
            assertEquals("TXN-XYZ-999", pago.getCodigoTransaccion());
            assertSame(metodo,          pago.getMetodoPago());
            assertSame(estado,          pago.getEstadoPago());
        }

        @Test
        @DisplayName("MetodoPago y EstadoPago asociados deben ser accesibles y correctos")
        void relaciones_MetodoPagoYEstadoPago_DebenSerAccesibles() {
            MetodoPago metodo = new MetodoPago(2L, "Debito", "Pago con debito");
            EstadoPago estado = new EstadoPago(3L, "RECHAZADO", "Pago rechazado por fondos insuficientes");

            Pago pago = new Pago();
            pago.setMetodoPago(metodo);
            pago.setEstadoPago(estado);

            assertNotNull(pago.getMetodoPago());
            assertNotNull(pago.getEstadoPago());
            assertEquals("Debito",    pago.getMetodoPago().getNombreMetodo());
            assertEquals("RECHAZADO", pago.getEstadoPago().getNombreEstado());
            assertEquals(2L,          pago.getMetodoPago().getIdMetodoPago());
            assertEquals(3L,          pago.getEstadoPago().getIdEstadoPago());
        }

        @Test
        @DisplayName("Setters deben aceptar valores nulos en todos los campos")
        void setters_DebemAceptarValoresNulos() {
            LocalDateTime ahora = LocalDateTime.now();
            Pago pago = new Pago(1L, 1L, 1L, 1000, ahora, "TXN-001", metodoPago(), estadoPago());

            pago.setIdPago(null);
            pago.setIdUsuario(null);
            pago.setIdProducto(null);
            pago.setMonto(null);
            pago.setFechaPago(null);
            pago.setCodigoTransaccion(null);
            pago.setMetodoPago(null);
            pago.setEstadoPago(null);

            assertNull(pago.getIdPago());
            assertNull(pago.getIdUsuario());
            assertNull(pago.getIdProducto());
            assertNull(pago.getMonto());
            assertNull(pago.getFechaPago());
            assertNull(pago.getCodigoTransaccion());
            assertNull(pago.getMetodoPago());
            assertNull(pago.getEstadoPago());
        }

        @Test
        @DisplayName("FechaPago debe poder representar cualquier instante valido")
        void fechaPago_DebeRepresentarCualquierInstante() {
            Pago pago = new Pago();
            LocalDateTime fecha1 = LocalDateTime.of(2020, 1, 1, 0, 0);
            LocalDateTime fecha2 = LocalDateTime.of(2099, 12, 31, 23, 59, 59);

            pago.setFechaPago(fecha1);
            assertEquals(fecha1, pago.getFechaPago());

            pago.setFechaPago(fecha2);
            assertEquals(fecha2, pago.getFechaPago());
        }

        @Test
        @DisplayName("Codigo de transaccion debe aceptar cualquier formato de string")
        void codigoTransaccion_DebeAceptarCualquierFormato() {
            Pago pago = new Pago();

            pago.setCodigoTransaccion("550e8400-e29b-41d4-a716-446655440000"); // UUID
            assertEquals("550e8400-e29b-41d4-a716-446655440000", pago.getCodigoTransaccion());

            pago.setCodigoTransaccion("TXN-20250619-001");                    // formato custom
            assertEquals("TXN-20250619-001", pago.getCodigoTransaccion());

            pago.setCodigoTransaccion("");                                      // string vacio
            assertEquals("", pago.getCodigoTransaccion());
        }
    }
}
