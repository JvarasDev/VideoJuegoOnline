package cl.videojuego.arma_service.service;

import cl.videojuego.arma_service.dto.ArmaDTO;
import cl.videojuego.arma_service.dto.ArmaRegistroDTO;
import cl.videojuego.arma_service.mapper.ArmaMapper;
import cl.videojuego.arma_service.model.Arma;
import cl.videojuego.arma_service.model.RarezaArma;
import cl.videojuego.arma_service.model.TipoArma;
import cl.videojuego.arma_service.repository.ArmaRepository;
import cl.videojuego.arma_service.repository.RarezaArmaRepository;
import cl.videojuego.arma_service.repository.TipoArmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("null")
@RequiredArgsConstructor
public class ArmaService {

    private final ArmaRepository armaRepository;

    private final TipoArmaRepository tipoArmaRepository;

    private final RarezaArmaRepository rarezaArmaRepository;


    // Listar todas las armas
    public List<ArmaDTO> listarTodas() {

        return armaRepository.findAll()
                .stream()
                .map(ArmaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Registrar nueva arma
    public ArmaDTO registrar(ArmaRegistroDTO dto) {

        // Buscar tipo arma
        TipoArma tipoArma = tipoArmaRepository.findById(dto.getIdTipoArma())
                .orElseThrow(() ->
                        new RuntimeException("Tipo de arma no encontrado")
                );

        // Buscar rareza arma
        RarezaArma rarezaArma = rarezaArmaRepository.findById(dto.getIdRarezaArma())
                .orElseThrow(() ->
                        new RuntimeException("Rareza no encontrada")
                );

        // Crear nueva entidad arma
        Arma arma = new Arma();

        arma.setNombreArma(dto.getNombreArma());

        arma.setDanio(dto.getDanio());

        arma.setNivelMinimo(dto.getNivelMinimo());

        arma.setPrecio(dto.getPrecio());

        arma.setTipoArma(tipoArma);

        arma.setRarezaArma(rarezaArma);

        // Guardar en MySQL
        Arma guardada = armaRepository.save(arma);

        // Convertir a DTO
        return ArmaMapper.toDTO(guardada);
    }

    // Buscar armas por tipo
    public List<ArmaDTO> listarPorTipo(Long idTipoArma) {

        return armaRepository.findByTipoArma_IdTipoArma(idTipoArma)
                .stream()
                .map(ArmaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar armas por rareza
    public List<ArmaDTO> listarPorRareza(Long idRarezaArma) {

        return armaRepository.findByRarezaArma_IdRarezaArma(idRarezaArma)
                .stream()
                .map(ArmaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar armas por nivel mínimo
    public List<ArmaDTO> listarPorNivel(Integer nivelMinimo) {

        return armaRepository.findByNivelMinimo(nivelMinimo)
                .stream()
                .map(ArmaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar armas por nombre parecido
    public List<ArmaDTO> buscarPorNombre(String nombreArma) {

        return armaRepository.findByNombreArmaContainingIgnoreCase(nombreArma)
                .stream()
                .map(ArmaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar armas según precio máximo
    public List<ArmaDTO> buscarPorPrecio(Integer precio) {

        return armaRepository.findByPrecioLessThanEqual(precio)
                .stream()
                .map(ArmaMapper::toDTO)
                .collect(Collectors.toList());
    }
    // Buscar arma por ID
    public ArmaDTO buscarPorId(Long idArma) {

        Arma arma = armaRepository.findById(idArma)
                .orElseThrow(() ->
                        new RuntimeException("Arma no encontrada")
                );

        return ArmaMapper.toDTO(arma);
    }
}