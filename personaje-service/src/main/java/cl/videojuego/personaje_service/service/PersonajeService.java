package cl.videojuego.personaje_service.service;

import cl.videojuego.personaje_service.client.UsuarioClient;
import cl.videojuego.personaje_service.dto.PersonajeDTO;
import cl.videojuego.personaje_service.dto.PersonajeRegistroDTO;
import cl.videojuego.personaje_service.dto.UsuarioDTO;
import cl.videojuego.personaje_service.mapper.PersonajeMapper;
import cl.videojuego.personaje_service.model.ClasePersonaje;
import cl.videojuego.personaje_service.model.EstadoPersonaje;
import cl.videojuego.personaje_service.model.Personaje;
import cl.videojuego.personaje_service.repository.ClasePersonajeRepository;
import cl.videojuego.personaje_service.repository.EstadoPersonajeRepository;
import cl.videojuego.personaje_service.repository.PersonajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import cl.videojuego.personaje_service.exception.UsuarioNoAptoException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonajeService {

    private final PersonajeRepository personajeRepository;
    private final ClasePersonajeRepository clasePersonajeRepository;
    private final EstadoPersonajeRepository estadoPersonajeRepository;
    private final UsuarioClient usuarioClient;


    public List<PersonajeDTO> listarTodos() {
        return personajeRepository.findAll()
                .stream()
                .map(PersonajeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PersonajeDTO registrar(PersonajeRegistroDTO dto) {

        UsuarioDTO usuario = usuarioClient.buscarUsuarioPorId(dto.getIdUsuario());

        if (usuario.getNombreEstado().equalsIgnoreCase("BANEADO")) {
            throw new UsuarioNoAptoException("No se puede crear personaje porque el usuario está baneado");
        }

        if (usuario.getNombreEstado().equalsIgnoreCase("SUSPENDIDO")) {
            throw new UsuarioNoAptoException("No se puede crear personaje porque el usuario está suspendido");
        }

        if (usuario.getNombreRol().equalsIgnoreCase("ADMIN")) {
            throw new UsuarioNoAptoException("No se puede crear personaje porque los administradores no juegan como personajes");
        }

        ClasePersonaje clase = clasePersonajeRepository.findById(dto.getIdClasePersonaje())
                .orElseThrow(() ->
                        new RuntimeException("Clase no encontrada")
                );

        EstadoPersonaje estado = estadoPersonajeRepository.findById(dto.getIdEstadoPersonaje())
                .orElseThrow(() ->
                        new RuntimeException("Estado no encontrado")
                );

        Personaje personaje = new Personaje();

        personaje.setNombre(dto.getNombre());
        personaje.setNivel(1);
        personaje.setVida(100);
        personaje.setMana(50);
        personaje.setIdUsuario(dto.getIdUsuario());
        personaje.setClasePersonaje(clase);
        personaje.setEstadoPersonaje(estado);

        Personaje guardado = personajeRepository.save(personaje);

        return PersonajeMapper.toDTO(guardado);
    }

    public List<PersonajeDTO> listarPorUsuario(Long idUsuario) {
        return personajeRepository.findByIdUsuario(idUsuario)
                .stream()
                .map(PersonajeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PersonajeDTO> listarPorNivel(Integer nivel) {
        return personajeRepository.findByNivel(nivel)
                .stream()
                .map(PersonajeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PersonajeDTO> listarPorClase(Long idClasePersonaje) {
        return personajeRepository.findByClasePersonaje_IdClasePersonaje(idClasePersonaje)
                .stream()
                .map(PersonajeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PersonajeDTO> listarPorEstado(Long idEstadoPersonaje) {
        return personajeRepository.findByEstadoPersonaje_IdEstadoPersonaje(idEstadoPersonaje)
                .stream()
                .map(PersonajeMapper::toDTO)
                .collect(Collectors.toList());
    }
    public PersonajeDTO buscarPorId(Long idPersonaje) {
        Personaje personaje = personajeRepository.findById(idPersonaje)
                .orElseThrow(() -> new RuntimeException("Personaje no encontrado"));

        return PersonajeMapper.toDTO(personaje);
    }
}
