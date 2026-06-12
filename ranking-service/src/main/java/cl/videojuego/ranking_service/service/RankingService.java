package cl.videojuego.ranking_service.service;

import cl.videojuego.ranking_service.client.PersonajeClient;
import cl.videojuego.ranking_service.dto.PersonajeDTO;
import cl.videojuego.ranking_service.dto.RankingDTO;
import cl.videojuego.ranking_service.dto.RankingRegistroDTO;
import cl.videojuego.ranking_service.mapper.RankingMapper;
import cl.videojuego.ranking_service.model.Liga;
import cl.videojuego.ranking_service.model.Ranking;
import cl.videojuego.ranking_service.model.Temporada;
import cl.videojuego.ranking_service.repository.LigaRepository;
import cl.videojuego.ranking_service.repository.RankingRepository;
import cl.videojuego.ranking_service.repository.TemporadaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingRepository rankingRepository;
    private final TemporadaRepository temporadaRepository;
    private final LigaRepository ligaRepository;
    private final PersonajeClient personajeClient;


    public List<RankingDTO> listarTodos() {
        return rankingRepository.findAll()
                .stream()
                .map(ranking -> {
                    PersonajeDTO personaje = personajeClient.buscarPersonajePorId(ranking.getIdPersonaje());
                    return RankingMapper.toDTO(ranking, personaje);
                })
                .collect(Collectors.toList());
    }

    public RankingDTO registrar(RankingRegistroDTO dto) {

        PersonajeDTO personaje = personajeClient.buscarPersonajePorId(dto.getIdPersonaje());

        Temporada temporada = temporadaRepository.findById(dto.getIdTemporada())
                .orElseThrow(() -> new RuntimeException("Temporada no encontrada"));

        Liga liga = ligaRepository.findById(dto.getIdLiga())
                .orElseThrow(() -> new RuntimeException("Liga no encontrada"));

        Ranking ranking = new Ranking();

        ranking.setIdPersonaje(dto.getIdPersonaje());
        ranking.setPuntos(dto.getPuntos());
        ranking.setVictorias(dto.getVictorias());
        ranking.setDerrotas(dto.getDerrotas());
        ranking.setPosicion(dto.getPosicion());
        ranking.setTemporada(temporada);
        ranking.setLiga(liga);

        Ranking guardado = rankingRepository.save(ranking);

        return RankingMapper.toDTO(guardado, personaje);
    }

    public List<RankingDTO> listarPorPersonaje(Long idPersonaje) {
        return rankingRepository.findByIdPersonaje(idPersonaje)
                .stream()
                .map(ranking -> {
                    PersonajeDTO personaje = personajeClient.buscarPersonajePorId(ranking.getIdPersonaje());
                    return RankingMapper.toDTO(ranking, personaje);
                })
                .collect(Collectors.toList());
    }

    public List<RankingDTO> listarPorLiga(Long idLiga) {
        return rankingRepository.findByLiga_IdLiga(idLiga)
                .stream()
                .map(ranking -> {
                    PersonajeDTO personaje = personajeClient.buscarPersonajePorId(ranking.getIdPersonaje());
                    return RankingMapper.toDTO(ranking, personaje);
                })
                .collect(Collectors.toList());
    }

    public List<RankingDTO> listarPorTemporada(Long idTemporada) {
        return rankingRepository.findByTemporada_IdTemporada(idTemporada)
                .stream()
                .map(ranking -> {
                    PersonajeDTO personaje = personajeClient.buscarPersonajePorId(ranking.getIdPersonaje());
                    return RankingMapper.toDTO(ranking, personaje);
                })
                .collect(Collectors.toList());
    }

    public List<RankingDTO> listarPorPuntosMinimos(Integer puntos) {
        return rankingRepository.findByPuntosGreaterThanEqual(puntos)
                .stream()
                .map(ranking -> {
                    PersonajeDTO personaje = personajeClient.buscarPersonajePorId(ranking.getIdPersonaje());
                    return RankingMapper.toDTO(ranking, personaje);
                })
                .collect(Collectors.toList());
    }

    public List<RankingDTO> listarTop10() {
        return rankingRepository.findTop10ByOrderByPuntosDesc()
                .stream()
                .map(ranking -> {
                    PersonajeDTO personaje = personajeClient.buscarPersonajePorId(ranking.getIdPersonaje());
                    return RankingMapper.toDTO(ranking, personaje);
                })
                .collect(Collectors.toList());
    }
    public RankingDTO buscarPorId(Long idRanking) {

        Ranking ranking = rankingRepository.findById(idRanking)
                .orElseThrow(() ->
                        new RuntimeException("Ranking no encontrado")
                );

        PersonajeDTO personaje =
                personajeClient.buscarPersonajePorId(
                        ranking.getIdPersonaje()
                );

        return RankingMapper.toDTO(ranking, personaje);
    }
}