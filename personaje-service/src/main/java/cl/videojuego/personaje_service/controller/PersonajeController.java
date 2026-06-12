package cl.videojuego.personaje_service.controller;

import cl.videojuego.personaje_service.dto.PersonajeDTO;
import cl.videojuego.personaje_service.dto.PersonajeRegistroDTO;
import cl.videojuego.personaje_service.model.ClasePersonaje;
import cl.videojuego.personaje_service.model.EstadoPersonaje;
import cl.videojuego.personaje_service.service.ClasePersonajeService;
import cl.videojuego.personaje_service.service.EstadoPersonajeService;
import cl.videojuego.personaje_service.service.PersonajeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personajes")
@RequiredArgsConstructor
public class PersonajeController {

    private final PersonajeService personajeService;

    private final ClasePersonajeService clasePersonajeService;

    private final EstadoPersonajeService estadoPersonajeService;


    @GetMapping
    public ResponseEntity<List<PersonajeDTO>> listarTodos() {
        return ResponseEntity.ok(personajeService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<PersonajeDTO> registrar(
            @Valid @RequestBody PersonajeRegistroDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personajeService.registrar(dto));
    }

    @GetMapping("/clases")
    public ResponseEntity<List<ClasePersonaje>> listarClases() {
        return ResponseEntity.ok(clasePersonajeService.listarClases());
    }

    @GetMapping("/estados")
    public ResponseEntity<List<EstadoPersonaje>> listarEstados() {
        return ResponseEntity.ok(estadoPersonajeService.listarEstados());
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PersonajeDTO>> listarPorUsuario(
            @PathVariable Long idUsuario
    ) {
        return ResponseEntity.ok(personajeService.listarPorUsuario(idUsuario));
    }

    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<PersonajeDTO>> listarPorNivel(
            @PathVariable Integer nivel
    ) {
        return ResponseEntity.ok(personajeService.listarPorNivel(nivel));
    }
    @GetMapping("/clase/{idClasePersonaje}")
    public ResponseEntity<List<PersonajeDTO>> listarPorClase(
            @PathVariable Long idClasePersonaje
    ) {
        return ResponseEntity.ok(personajeService.listarPorClase(idClasePersonaje));
    }

    @GetMapping("/estado/{idEstadoPersonaje}")
    public ResponseEntity<List<PersonajeDTO>> listarPorEstado(
            @PathVariable Long idEstadoPersonaje
    ) {
        return ResponseEntity.ok(personajeService.listarPorEstado(idEstadoPersonaje));
    }
    @GetMapping("/{idPersonaje}")
    public ResponseEntity<PersonajeDTO> buscarPorId(@PathVariable Long idPersonaje) {
        return ResponseEntity.ok(personajeService.buscarPorId(idPersonaje));
    }
}
