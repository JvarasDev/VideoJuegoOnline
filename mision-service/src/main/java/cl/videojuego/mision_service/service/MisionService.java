package cl.videojuego.mision_service.service;

import cl.videojuego.mision_service.dto.MisionDTO;
import cl.videojuego.mision_service.dto.MisionRegistroDTO;
import cl.videojuego.mision_service.mapper.MisionMapper;
import cl.videojuego.mision_service.model.EstadoMision;
import cl.videojuego.mision_service.model.Mision;
import cl.videojuego.mision_service.model.TipoMision;
import cl.videojuego.mision_service.repository.EstadoMisionRepository;
import cl.videojuego.mision_service.repository.MisionRepository;
import cl.videojuego.mision_service.repository.TipoMisionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// @Service indica que esta clase contiene la lógica de negocio
@Service
@SuppressWarnings("null")
@RequiredArgsConstructor
public class MisionService {

    private final MisionRepository misionRepository;
    private final TipoMisionRepository tipoMisionRepository;
    private final EstadoMisionRepository estadoMisionRepository;


    // Lista todas las misiones
    public List<MisionDTO> listarTodas() {
        return misionRepository.findAll()
                .stream()
                .map(MisionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Registra una nueva misión
    public MisionDTO registrar(MisionRegistroDTO dto) {

        TipoMision tipo = tipoMisionRepository.findById(dto.getIdTipoMision())
                .orElseThrow(() -> new RuntimeException("Tipo de misión no encontrado"));

        EstadoMision estado = estadoMisionRepository.findById(dto.getIdEstadoMision())
                .orElseThrow(() -> new RuntimeException("Estado de misión no encontrado"));

        Mision mision = new Mision();

        mision.setNombreMision(dto.getNombreMision());
        mision.setDescripcion(dto.getDescripcion());
        mision.setRecompensaExperiencia(dto.getRecompensaExperiencia());
        mision.setRecompensaMonedas(dto.getRecompensaMonedas());
        mision.setNivelMinimo(dto.getNivelMinimo());
        mision.setTipoMision(tipo);
        mision.setEstadoMision(estado);

        Mision guardada = misionRepository.save(mision);

        return MisionMapper.toDTO(guardada);
    }

    // Buscar misiones por tipo
    public List<MisionDTO> listarPorTipo(Long idTipoMision) {
        return misionRepository.findByTipoMision_IdTipoMision(idTipoMision)
                .stream()
                .map(MisionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar misiones por estado
    public List<MisionDTO> listarPorEstado(Long idEstadoMision) {
        return misionRepository.findByEstadoMision_IdEstadoMision(idEstadoMision)
                .stream()
                .map(MisionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar misiones por nivel mínimo exacto
    public List<MisionDTO> listarPorNivel(Integer nivelMinimo) {
        return misionRepository.findByNivelMinimo(nivelMinimo)
                .stream()
                .map(MisionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar misiones por nombre parecido
    public List<MisionDTO> buscarPorNombre(String nombre) {
        return misionRepository.findByNombreMisionContainingIgnoreCase(nombre)
                .stream()
                .map(MisionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar misiones por recompensa máxima en monedas
    public List<MisionDTO> buscarPorRecompensa(Integer recompensa) {
        return misionRepository.findByRecompensaMonedasLessThanEqual(recompensa)
                .stream()
                .map(MisionMapper::toDTO)
                .collect(Collectors.toList());
    }
}