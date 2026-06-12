package cl.videojuego.pago_service.controller;

import cl.videojuego.pago_service.dto.PagoDTO;
import cl.videojuego.pago_service.dto.PagoRegistroDTO;
import cl.videojuego.pago_service.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<PagoDTO>> listarTodos() {
        return ResponseEntity.ok(pagoService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<PagoDTO> registrar(@Valid @RequestBody PagoRegistroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.registrar(dto));
    }

    @GetMapping("/{idPago}")
    public ResponseEntity<PagoDTO> buscarPorId(@PathVariable Long idPago) {
        return ResponseEntity.ok(pagoService.buscarPorId(idPago));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PagoDTO>> listarPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(pagoService.listarPorUsuario(idUsuario));
    }
}