package cl.videojuego.inventario_service.service;

import cl.videojuego.inventario_service.client.PersonajeClient;
import cl.videojuego.inventario_service.dto.InventarioDTO;
import cl.videojuego.inventario_service.dto.InventarioRegistroDTO;
import cl.videojuego.inventario_service.dto.PersonajeDTO;
import cl.videojuego.inventario_service.mapper.InventarioMapper;
import cl.videojuego.inventario_service.model.EstadoInventario;
import cl.videojuego.inventario_service.model.Inventario;
import cl.videojuego.inventario_service.repository.EstadoInventarioRepository;
import cl.videojuego.inventario_service.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import cl.videojuego.inventario_service.exception.RecursoNoEncontradoException;
import cl.videojuego.inventario_service.exception.ConflictoNegocioException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("null")
@RequiredArgsConstructor
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private final EstadoInventarioRepository estadoInventarioRepository;
    private final PersonajeClient personajeClient;


    public List<InventarioDTO> listarTodos() {
        return inventarioRepository.findAll()
                .stream()
                .map(inventario -> {
                    PersonajeDTO personaje = personajeClient.buscarPersonajePorId(inventario.getIdPersonaje());
                    return InventarioMapper.toDTO(inventario, personaje);
                })
                .collect(Collectors.toList());
    }

    public InventarioDTO registrar(InventarioRegistroDTO dto) {

        PersonajeDTO personaje = personajeClient.buscarPersonajePorId(dto.getIdPersonaje());

        if (inventarioRepository.existsByIdPersonaje(dto.getIdPersonaje())) {
            throw new ConflictoNegocioException("El personaje con ID " + dto.getIdPersonaje() + " ya tiene un inventario.");
        }

        EstadoInventario estado = estadoInventarioRepository.findById(dto.getIdEstadoInventario())
                .orElseThrow(() -> new RecursoNoEncontradoException("Estado de inventario no encontrado"));

        Inventario inventario = new Inventario();

        inventario.setIdPersonaje(dto.getIdPersonaje());
        inventario.setCapacidadMaxima(dto.getCapacidadMaxima());
        inventario.setEspaciosUsados(0);
        inventario.setFechaCreacion(LocalDate.now());
        inventario.setEstadoInventario(estado);

        Inventario guardado = inventarioRepository.save(inventario);

        return InventarioMapper.toDTO(guardado, personaje);
    }

    public List<InventarioDTO> listarPorPersonaje(Long idPersonaje) {
        List<Inventario> inventarios = inventarioRepository.findByIdPersonaje(idPersonaje);
        if (inventarios.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontraron inventarios para el personaje con ID: " + idPersonaje);
        }
        return inventarios.stream()
                .map(inventario -> {
                    PersonajeDTO personaje = personajeClient.buscarPersonajePorId(inventario.getIdPersonaje());
                    return InventarioMapper.toDTO(inventario, personaje);
                })
                .collect(Collectors.toList());
    }

    public List<InventarioDTO> listarPorEstado(Long idEstadoInventario) {
        List<Inventario> inventarios = inventarioRepository.findByEstadoInventario_IdEstadoInventario(idEstadoInventario);
        if (inventarios.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontraron inventarios para el estado con ID: " + idEstadoInventario);
        }
        return inventarios.stream()
                .map(inventario -> {
                    PersonajeDTO personaje = personajeClient.buscarPersonajePorId(inventario.getIdPersonaje());
                    return InventarioMapper.toDTO(inventario, personaje);
                })
                .collect(Collectors.toList());
    }
    public InventarioDTO buscarPorId(Long idInventario) {

        Inventario inventario = inventarioRepository.findById(idInventario)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Inventario no encontrado")
                );

        PersonajeDTO personaje =
                personajeClient.buscarPersonajePorId(
                        inventario.getIdPersonaje()
                );

        return InventarioMapper.toDTO(inventario, personaje);
    }

}