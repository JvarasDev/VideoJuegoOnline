package cl.videojuego.ranking_service.controller;

import cl.videojuego.ranking_service.dto.RankingDTO;
import cl.videojuego.ranking_service.dto.RankingRegistroDTO;
import cl.videojuego.ranking_service.service.RankingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rankings")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;

    @GetMapping
    public ResponseEntity<List<RankingDTO>> listarTodos() {
        return ResponseEntity.ok(rankingService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<RankingDTO> registrar(
            @Valid @RequestBody RankingRegistroDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rankingService.registrar(dto));
    }

    @GetMapping("/personaje/{idPersonaje}")
    public ResponseEntity<List<RankingDTO>> listarPorPersonaje(
            @PathVariable Long idPersonaje
    ) {
        return ResponseEntity.ok(rankingService.listarPorPersonaje(idPersonaje));
    }

    @GetMapping("/liga/{idLiga}")
    public ResponseEntity<List<RankingDTO>> listarPorLiga(
            @PathVariable Long idLiga
    ) {
        return ResponseEntity.ok(rankingService.listarPorLiga(idLiga));
    }

    @GetMapping("/temporada/{idTemporada}")
    public ResponseEntity<List<RankingDTO>> listarPorTemporada(
            @PathVariable Long idTemporada
    ) {
        return ResponseEntity.ok(rankingService.listarPorTemporada(idTemporada));
    }

    @GetMapping("/puntos-minimos")
    public ResponseEntity<List<RankingDTO>> listarPorPuntosMinimos(
            @RequestParam Integer puntos
    ) {
        return ResponseEntity.ok(rankingService.listarPorPuntosMinimos(puntos));
    }

    @GetMapping("/top10")
    public ResponseEntity<List<RankingDTO>> listarTop10() {
        return ResponseEntity.ok(rankingService.listarTop10());
    }

    @GetMapping("/{idRanking}")
    public ResponseEntity<RankingDTO> buscarPorId(
            @PathVariable Long idRanking
    ) {
        return ResponseEntity.ok(rankingService.buscarPorId(idRanking));
    }
}