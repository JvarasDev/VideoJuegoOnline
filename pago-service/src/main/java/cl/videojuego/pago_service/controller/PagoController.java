package cl.videojuego.pago_service.controller;

import cl.videojuego.pago_service.dto.PagoDTO;
import cl.videojuego.pago_service.dto.PagoRegistroDTO;
import cl.videojuego.pago_service.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
@Tag(name = "Pagos", description = "Operaciones relacionadas con los pagos de la  tienda del videojuego")
public class PagoController {

    private final PagoService pagoService;


    @Operation(summary = "Listar todos los pagos", description = "Retorna una lista completa con todos los pagos registrados en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de pagos obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<PagoDTO>> listarTodos() {
        return ResponseEntity.ok(pagoService.listarTodos());
    }

    @Operation(summary = "Registrar un nuevo pago", description = "Crea un nuevo registro de pago en el sistema")
    @PostMapping
    public ResponseEntity<PagoDTO> registrar(@Valid @RequestBody PagoRegistroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.registrar(dto));
    }

    @Operation(summary = "Buscar pago por ID", description = "Permite obtener la información detallada de un pago específico mediante su identificador")
    @GetMapping("/{idPago}")
    public ResponseEntity<PagoDTO> buscarPorId(@PathVariable Long idPago) {
        return ResponseEntity.ok(pagoService.buscarPorId(idPago));
    }

    @Operation(summary = "Listar pagos por usuario", description = "Retorna todos los pagos asociados a un usuario")
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PagoDTO>> listarPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(pagoService.listarPorUsuario(idUsuario));
    }
}