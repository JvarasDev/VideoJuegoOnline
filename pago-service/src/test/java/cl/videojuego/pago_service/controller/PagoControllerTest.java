package cl.videojuego.pago_service.controller;

import cl.videojuego.pago_service.dto.PagoDTO;
import cl.videojuego.pago_service.dto.PagoRegistroDTO;
import cl.videojuego.pago_service.dto.ComprobanteDTO;
import cl.videojuego.pago_service.exception.PagoNotFoundException;
import cl.videojuego.pago_service.exception.StockInsuficienteException;
import cl.videojuego.pago_service.service.PagoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PagoController.class)
class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagoService pagoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listarTodos_DeberiaRetornar200YListaDePagos() throws Exception {
        // Given
        PagoDTO pago1 = new PagoDTO();
        pago1.setIdPago(1L);
        PagoDTO pago2 = new PagoDTO();
        pago2.setIdPago(2L);

        when(pagoService.listarTodos()).thenReturn(List.of(pago1, pago2));

        // When & Then
        mockMvc.perform(get("/api/pagos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].idPago").value(1))
                .andExpect(jsonPath("$[1].idPago").value(2));

        verify(pagoService, times(1)).listarTodos();
    }

    @Test
    void registrar_CuandoDatosSonValidos_DeberiaRetornar201YPagoCreado() throws Exception {
        // Given
        PagoRegistroDTO registroDTO = new PagoRegistroDTO();
        registroDTO.setIdUsuario(1L);
        registroDTO.setIdProducto(1L);
        registroDTO.setIdMetodoPago(1L);
        registroDTO.setIdEstadoPago(1L);
        registroDTO.setMonto(100);

        PagoDTO pagoCreado = new PagoDTO();
        pagoCreado.setIdPago(1L);
        pagoCreado.setMonto(100);

        when(pagoService.registrar(any(PagoRegistroDTO.class))).thenReturn(pagoCreado);

        // When & Then
        mockMvc.perform(post("/api/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registroDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idPago").value(1))
                .andExpect(jsonPath("$.monto").value(100));

        verify(pagoService, times(1)).registrar(any(PagoRegistroDTO.class));
    }

    @Test
    void registrar_CuandoFaltanDatosObligatorios_DeberiaRetornar400() throws Exception {
        // Given
        PagoRegistroDTO registroDTOIncompleto = new PagoRegistroDTO();
        // Al estar vacío, fallará las validaciones @Valid del Controller

        // When & Then
        mockMvc.perform(post("/api/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registroDTOIncompleto)))
                .andExpect(status().isBadRequest());

        verify(pagoService, never()).registrar(any(PagoRegistroDTO.class));
    }

    @Test
    void registrar_CuandoStockInsuficiente_DeberiaRetornar409Conflict() throws Exception {
        // Given
        PagoRegistroDTO registroDTO = new PagoRegistroDTO();
        registroDTO.setIdUsuario(1L);
        registroDTO.setIdProducto(1L);
        registroDTO.setIdMetodoPago(1L);
        registroDTO.setIdEstadoPago(1L);
        registroDTO.setMonto(100);

        when(pagoService.registrar(any(PagoRegistroDTO.class)))
                .thenThrow(new StockInsuficienteException(1L));

        // When & Then
        mockMvc.perform(post("/api/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registroDTO)))
                .andExpect(status().isConflict()); // HTTP 409

        verify(pagoService, times(1)).registrar(any(PagoRegistroDTO.class));
    }

    @Test
    void buscarPorId_CuandoPagoExiste_DeberiaRetornar200YPagoDTO() throws Exception {
        // Given
        Long idPago = 1L;
        PagoDTO pagoDTO = new PagoDTO();
        pagoDTO.setIdPago(idPago);

        when(pagoService.buscarPorId(idPago)).thenReturn(pagoDTO);

        // When & Then
        mockMvc.perform(get("/api/pagos/{idPago}", idPago)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPago").value(idPago));

        verify(pagoService, times(1)).buscarPorId(idPago);
    }

    @Test
    void buscarPorId_CuandoPagoNoExiste_DeberiaRetornar404() throws Exception {
        // Given
        Long idPago = 1L;
        when(pagoService.buscarPorId(idPago)).thenThrow(new PagoNotFoundException(idPago));

        // When & Then
        mockMvc.perform(get("/api/pagos/{idPago}", idPago)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // HTTP 404

        verify(pagoService, times(1)).buscarPorId(idPago);
    }

    @Test
    void listarPorUsuario_DeberiaRetornar200YListaDePagos() throws Exception {
        // Given
        Long idUsuario = 1L;
        PagoDTO pagoDTO = new PagoDTO();
        pagoDTO.setIdPago(1L);

        when(pagoService.listarPorUsuario(idUsuario)).thenReturn(List.of(pagoDTO));

        // When & Then
        mockMvc.perform(get("/api/pagos/usuario/{idUsuario}", idUsuario)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].idPago").value(1));

        verify(pagoService, times(1)).listarPorUsuario(idUsuario);
    }

    @Test
    void listarPorEstado_DeberiaRetornar200YListaDePagos() throws Exception {
        // Given
        Long idEstadoPago = 1L;
        PagoDTO pagoDTO = new PagoDTO();
        pagoDTO.setIdPago(1L);

        when(pagoService.listarPorEstado(idEstadoPago)).thenReturn(List.of(pagoDTO));

        // When & Then
        mockMvc.perform(get("/api/pagos/estado/{idEstadoPago}", idEstadoPago)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].idPago").value(1));

        verify(pagoService, times(1)).listarPorEstado(idEstadoPago);
    }

    @Test
    void generarComprobante_CuandoPagoExiste_DeberiaRetornar200YComprobanteDTO() throws Exception {
        // Given
        Long idPago = 1L;
        ComprobanteDTO comprobante = new ComprobanteDTO();
        comprobante.setIdPago(idPago);
        comprobante.setNombreCliente("Juan Perez");

        when(pagoService.generarComprobante(idPago)).thenReturn(comprobante);

        // When & Then
        mockMvc.perform(get("/api/pagos/{idPago}/comprobante", idPago)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPago").value(idPago))
                .andExpect(jsonPath("$.nombreCliente").value("Juan Perez"));

        verify(pagoService, times(1)).generarComprobante(idPago);
    }
}
