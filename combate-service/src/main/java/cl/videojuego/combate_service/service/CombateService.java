package cl.videojuego.combate_service.service;

import cl.videojuego.combate_service.client.PersonajeClient;
import cl.videojuego.combate_service.dto.CombateDTO;
import cl.videojuego.combate_service.dto.CombateRegistroDTO;
import cl.videojuego.combate_service.dto.PersonajeDTO;
import cl.videojuego.combate_service.mapper.CombateMapper;
import cl.videojuego.combate_service.model.Combate;
import cl.videojuego.combate_service.model.EstadoCombate;
import cl.videojuego.combate_service.model.TipoCombate;
import cl.videojuego.combate_service.repository.CombateRepository;
import cl.videojuego.combate_service.repository.EstadoCombateRepository;
import cl.videojuego.combate_service.repository.TipoCombateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import cl.videojuego.combate_service.exception.ResultadoCombateInvalidoException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("null")
@RequiredArgsConstructor
public class CombateService {

    private final CombateRepository combateRepository;
    private final TipoCombateRepository tipoCombateRepository;
    private final EstadoCombateRepository estadoCombateRepository;
    private final PersonajeClient personajeClient;


    public List<CombateDTO> listarTodos() {
        return combateRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public CombateDTO registrar(CombateRegistroDTO dto) {

        PersonajeDTO atacante = personajeClient.buscarPersonajePorId(dto.getIdPersonajeAtacante());
        PersonajeDTO defensor = personajeClient.buscarPersonajePorId(dto.getIdPersonajeDefensor());
        PersonajeDTO ganador = personajeClient.buscarPersonajePorId(dto.getIdGanador());

        if (!dto.getIdGanador().equals(dto.getIdPersonajeAtacante())
                && !dto.getIdGanador().equals(dto.getIdPersonajeDefensor())) {
            throw new ResultadoCombateInvalidoException("El ganador debe ser el atacante o el defensor");
        }

        TipoCombate tipo = tipoCombateRepository.findById(dto.getIdTipoCombate())
                .orElseThrow(() -> new RuntimeException("Tipo de combate no encontrado"));

        EstadoCombate estado = estadoCombateRepository.findById(dto.getIdEstadoCombate())
                .orElseThrow(() -> new RuntimeException("Estado de combate no encontrado"));

        Combate combate = new Combate();

        combate.setIdPersonajeAtacante(dto.getIdPersonajeAtacante());
        combate.setIdPersonajeDefensor(dto.getIdPersonajeDefensor());
        combate.setIdGanador(dto.getIdGanador());
        combate.setFechaCombate(LocalDateTime.now());
        combate.setExperienciaGanada(dto.getExperienciaGanada());
        combate.setMonedasGanadas(dto.getMonedasGanadas());
        combate.setDuracionSegundos(dto.getDuracionSegundos());
        combate.setTipoCombate(tipo);
        combate.setEstadoCombate(estado);

        Combate guardado = combateRepository.save(combate);

        return CombateMapper.toDTO(guardado, atacante, defensor, ganador);
    }

    public List<CombateDTO> listarPorAtacante(Long idPersonaje) {
        return combateRepository.findByIdPersonajeAtacante(idPersonaje)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<CombateDTO> listarPorDefensor(Long idPersonaje) {
        return combateRepository.findByIdPersonajeDefensor(idPersonaje)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<CombateDTO> listarPorGanador(Long idGanador) {
        return combateRepository.findByIdGanador(idGanador)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<CombateDTO> listarPorTipo(Long idTipoCombate) {
        return combateRepository.findByTipoCombate_IdTipoCombate(idTipoCombate)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<CombateDTO> listarPorEstado(Long idEstadoCombate) {
        return combateRepository.findByEstadoCombate_IdEstadoCombate(idEstadoCombate)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private CombateDTO convertirADTO(Combate combate) {
        PersonajeDTO atacante = personajeClient.buscarPersonajePorId(combate.getIdPersonajeAtacante());
        PersonajeDTO defensor = personajeClient.buscarPersonajePorId(combate.getIdPersonajeDefensor());
        PersonajeDTO ganador = personajeClient.buscarPersonajePorId(combate.getIdGanador());

        return CombateMapper.toDTO(combate, atacante, defensor, ganador);
    }
}