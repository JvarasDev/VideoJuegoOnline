package cl.videojuego.personaje_service.controller;

import cl.videojuego.personaje_service.dto.PersonajeDTO;
import cl.videojuego.personaje_service.dto.PersonajeRegistroDTO;
import cl.videojuego.personaje_service.model.ClasePersonaje;
import cl.videojuego.personaje_service.model.EstadoPersonaje;
import cl.videojuego.personaje_service.service.ClasePersonajeService;
import cl.videojuego.personaje_service.service.EstadoPersonajeService;
import cl.videojuego.personaje_service.service.PersonajeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personajes")
@RequiredArgsConstructor
@Tag(name = "Personajes", description = "Operaciones relacionadas con la gestiÃƒÂ³n de personajes del videojuego")
public class PersonajeController {

    private final PersonajeService personajeService;

    private final ClasePersonajeService clasePersonajeService;

    private final EstadoPersonajeService estadoPersonajeService;


    // Listar todos los personajes
    @Operation(
            summary = "Listar todos los personajes",
            description = "Retorna una lista completa con todos los personajes registrados en el sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Personajes listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<PersonajeDTO>> listarTodos() {
        return ResponseEntity.ok(personajeService.listarTodos());
    }


    //Registar nuevo personaje
    @Operation(summary = "Registrar nuevo personaje",
            description = "Permite crear un nuevo personaje asociado a un usuario existente. Antes de registrar, valida mediante OpenFeign que el usuario exista, no estÃƒÂ© baneado, no estÃƒÂ© suspendido y no sea administrador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Personaje registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invÃƒÂ¡lidos enviados en la solicitud"),
            @ApiResponse(responseCode = "404", description = "Usuario, clase o estado no encontrado"),
            @ApiResponse(responseCode = "409", description = "Usuario no apto para crear personaje"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<PersonajeDTO> registrar(
            @Valid @RequestBody PersonajeRegistroDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personajeService.registrar(dto));
    }



    //Listar clases de personaje
    @Operation(summary = "Listar clases de personaje",
               description = "Retorna todas las clases disponibles para los personajes, por ejemplo guerrero, mago o arquero."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Clases listadas correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/clases")
    public ResponseEntity<List<ClasePersonaje>> listarClases() {
        return ResponseEntity.ok(clasePersonajeService.listarClases());
    }



    //Listar Estados de los personajes

    @Operation(summary = "Listar estados de personaje",
               description = "Retorna todos los estados disponibles para los personajes, por ejemplo activo, inactivo o eliminado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estados listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/estados")
    public ResponseEntity<List<EstadoPersonaje>> listarEstados() {
        return ResponseEntity.ok(estadoPersonajeService.listarEstados());
    }



    // Listar personajes por Id de usuario
    @Operation(summary = "Listar personajes por usuario",
               description = "Retorna todos los personajes asociados a un usuario especÃƒÂ­fico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Personajes del usuario listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PersonajeDTO>> listarPorUsuario(
<<<<<<< HEAD
            @Parameter(description = "Identificador del usuario propietario de los personajes", example = "1")
            @PathVariable Long idUsuario
    ) {
=======

            @Parameter(description = "Identificador del usuario propietario de los personajes", example = "1")
            @PathVariable Long idUsuario) {
>>>>>>> a7c43fd2c70aa9e87c829e7285155016252c9d2f
        return ResponseEntity.ok(personajeService.listarPorUsuario(idUsuario));
    }


    //Listar personajes por nivel
    @Operation(
            summary = "Listar personajes por nivel",
            description = "Retorna personajes filtrados por un nivel especÃƒÂ­fico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Personajes por nivel listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<PersonajeDTO>> listarPorNivel(

            @Parameter(description = "Nivel del personaje", example = "10")
            @PathVariable Integer nivel
    ) {
        return ResponseEntity.ok(personajeService.listarPorNivel(nivel));
    }


    //Listar personajes por clase

    @Operation(summary = "Listar personajes por clase",
                description = "Retorna personajes filtrados por clase de personaje."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Personajes por clase listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/clase/{idClasePersonaje}")
    public ResponseEntity<List<PersonajeDTO>> listarPorClase(

            @Parameter(description = "Identificador de la clase del personaje", example = "1")
            @PathVariable Long idClasePersonaje
    ) {
        return ResponseEntity.ok(personajeService.listarPorClase(idClasePersonaje));
    }



    // Listar Personajes por Estado
    @Operation(summary = "Listar personajes por estado",
              description = "Retorna personajes filtrados por estado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Personajes por estado listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/estado/{idEstadoPersonaje}")
    public ResponseEntity<List<PersonajeDTO>> listarPorEstado(
            @Parameter(description = "Identificador del estado del personaje", example = "1")
            @PathVariable Long idEstadoPersonaje
    ) {
        return ResponseEntity.ok(personajeService.listarPorEstado(idEstadoPersonaje));
    }



    // Buscar personmaje por ID
    @Operation(
            summary = "Buscar personaje por ID",
            description = "Permite obtener la informaciÃƒÂ³n detallada de un personaje especÃƒÂ­fico mediante su identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Personaje encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Personaje no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{idPersonaje}")
    public ResponseEntity<PersonajeDTO> buscarPorId(
            @Parameter(description = "Identificador ÃƒÂºnico del personaje", example = "1")
            @PathVariable Long idPersonaje) {
        return ResponseEntity.ok(personajeService.buscarPorId(idPersonaje));
    }
}


