package cl.videojuego.ranking_service.service;

import cl.videojuego.ranking_service.client.PersonajeClient;
import cl.videojuego.ranking_service.dto.PersonajeDTO;
import cl.videojuego.ranking_service.dto.RankingDTO;
import cl.videojuego.ranking_service.dto.RankingRegistroDTO;
import cl.videojuego.ranking_service.model.Liga;
import cl.videojuego.ranking_service.model.Ranking;
import cl.videojuego.ranking_service.model.Temporada;
import cl.videojuego.ranking_service.repository.LigaRepository;
import cl.videojuego.ranking_service.repository.RankingRepository;
import cl.videojuego.ranking_service.repository.TemporadaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class RankingServiceTest {

    @Mock
    private RankingRepository rankingRepository;

    @Mock
    private TemporadaRepository temporadaRepository;

    @Mock
    private LigaRepository ligaRepository;

    @Mock
    private PersonajeClient personajeClient;

    @InjectMocks
    private RankingService rankingService;

    private Ranking ranking;
    private PersonajeDTO personajeDTO;
    private Temporada temporada;
    private Liga liga;
    private RankingRegistroDTO registroDTO;

    @BeforeEach
    void setUp() {
        temporada = new Temporada();
        temporada.setIdTemporada(1L);
        temporada.setNombreTemporada("Temporada 1");
        temporada.setFechaInicio(LocalDate.now().minusDays(10));
        temporada.setFechaFin(LocalDate.now().plusDays(10));

        liga = new Liga();
        liga.setIdLiga(1L);
        liga.setNombreLiga("Liga Bronce");
        liga.setPuntosMinimos(0);
        liga.setPuntosMaximos(999);

        ranking = new Ranking();
        ranking.setIdRanking(1L);
        ranking.setIdPersonaje(10L);
        ranking.setPuntos(1500);
        ranking.setVictorias(30);
        ranking.setDerrotas(10);
        ranking.setPosicion(5);
        ranking.setTemporada(temporada);
        ranking.setLiga(liga);

        personajeDTO = new PersonajeDTO();
        personajeDTO.setIdPersonaje(10L);
        personajeDTO.setNombre("Heroe");

        registroDTO = new RankingRegistroDTO();
        registroDTO.setIdPersonaje(10L);
        registroDTO.setIdTemporada(1L);
        registroDTO.setIdLiga(1L);
        registroDTO.setPuntos(1500);
        registroDTO.setVictorias(30);
        registroDTO.setDerrotas(10);
        registroDTO.setPosicion(5);
    }

    @Test
    @DisplayName("Debe listar todos los rankings correctamente")
    void shouldListarTodos_WhenRankingsExist_ReturnsRankingDTOList() {
        // Given
        given(rankingRepository.findAll()).willReturn(List.of(ranking));
        given(personajeClient.buscarPersonajePorId(10L)).willReturn(personajeDTO);

        // When
        List<RankingDTO> result = rankingService.listarTodos();

        // Then
        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.get(0).getIdRanking()).isEqualTo(1L);
        assertThat(result.get(0).getNombrePersonaje()).isEqualTo("Heroe");
        
        then(rankingRepository).should(times(1)).findAll();
        then(personajeClient).should(times(1)).buscarPersonajePorId(10L);
        then(rankingRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("Debe registrar un nuevo ranking de forma exitosa")
    void shouldRegistrar_WhenValidData_ReturnsRankingDTO() {
        // Given
        given(personajeClient.buscarPersonajePorId(10L)).willReturn(personajeDTO);
        given(temporadaRepository.findById(1L)).willReturn(Optional.of(temporada));
        given(ligaRepository.findById(1L)).willReturn(Optional.of(liga));
        given(rankingRepository.save(any(Ranking.class))).willReturn(ranking);

        // When
        RankingDTO result = rankingService.registrar(registroDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getIdRanking()).isEqualTo(1L);
        assertThat(result.getNombrePersonaje()).isEqualTo("Heroe");
        assertThat(result.getNombreLiga()).isEqualTo("Liga Bronce");

        then(personajeClient).should(times(1)).buscarPersonajePorId(10L);
        then(temporadaRepository).should(times(1)).findById(1L);
        then(ligaRepository).should(times(1)).findById(1L);
        then(rankingRepository).should(times(1)).save(any(Ranking.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción al intentar registrar con una temporada inexistente")
    void shouldThrowException_WhenRegistrarWithInvalidTemporada() {
        // Given
        registroDTO.setIdTemporada(99L);
        
        given(personajeClient.buscarPersonajePorId(10L)).willReturn(personajeDTO);
        given(temporadaRepository.findById(99L)).willReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> rankingService.registrar(registroDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Temporada no encontrada");

        then(personajeClient).should(times(1)).buscarPersonajePorId(10L);
        then(temporadaRepository).should(times(1)).findById(99L);
        then(ligaRepository).shouldHaveNoInteractions();
        then(rankingRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("Debe lanzar excepción al intentar registrar con una liga inexistente")
    void shouldThrowException_WhenRegistrarWithInvalidLiga() {
        // Given
        registroDTO.setIdLiga(99L);
        
        given(personajeClient.buscarPersonajePorId(10L)).willReturn(personajeDTO);
        given(temporadaRepository.findById(1L)).willReturn(Optional.of(temporada));
        given(ligaRepository.findById(99L)).willReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> rankingService.registrar(registroDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Liga no encontrada");

        then(personajeClient).should(times(1)).buscarPersonajePorId(10L);
        then(temporadaRepository).should(times(1)).findById(1L);
        then(ligaRepository).should(times(1)).findById(99L);
        then(rankingRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("Debe listar rankings por id de personaje")
    void shouldListarPorPersonaje_WhenExists_ReturnsRankingDTOList() {
        // Given
        given(rankingRepository.findByIdPersonaje(10L)).willReturn(List.of(ranking));
        given(personajeClient.buscarPersonajePorId(10L)).willReturn(personajeDTO);

        // When
        List<RankingDTO> result = rankingService.listarPorPersonaje(10L);

        // Then
        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.get(0).getIdRanking()).isEqualTo(1L);

        then(rankingRepository).should(times(1)).findByIdPersonaje(10L);
        then(personajeClient).should(times(1)).buscarPersonajePorId(10L);
    }

    @Test
    @DisplayName("Debe listar rankings por id de liga")
    void shouldListarPorLiga_WhenExists_ReturnsRankingDTOList() {
        // Given
        given(rankingRepository.findByLiga_IdLiga(1L)).willReturn(List.of(ranking));
        given(personajeClient.buscarPersonajePorId(10L)).willReturn(personajeDTO);

        // When
        List<RankingDTO> result = rankingService.listarPorLiga(1L);

        // Then
        assertThat(result).isNotNull().hasSize(1);
        
        then(rankingRepository).should(times(1)).findByLiga_IdLiga(1L);
    }

    @Test
    @DisplayName("Debe listar rankings por id de temporada")
    void shouldListarPorTemporada_WhenExists_ReturnsRankingDTOList() {
        // Given
        given(rankingRepository.findByTemporada_IdTemporada(1L)).willReturn(List.of(ranking));
        given(personajeClient.buscarPersonajePorId(10L)).willReturn(personajeDTO);

        // When
        List<RankingDTO> result = rankingService.listarPorTemporada(1L);

        // Then
        assertThat(result).isNotNull().hasSize(1);
        
        then(rankingRepository).should(times(1)).findByTemporada_IdTemporada(1L);
    }

    @Test
    @DisplayName("Debe listar rankings que tengan puntos iguales o superiores al mínimo")
    void shouldListarPorPuntosMinimos_WhenExists_ReturnsRankingDTOList() {
        // Given
        given(rankingRepository.findByPuntosGreaterThanEqual(1000)).willReturn(List.of(ranking));
        given(personajeClient.buscarPersonajePorId(10L)).willReturn(personajeDTO);

        // When
        List<RankingDTO> result = rankingService.listarPorPuntosMinimos(1000);

        // Then
        assertThat(result).isNotNull().hasSize(1);
        
        then(rankingRepository).should(times(1)).findByPuntosGreaterThanEqual(1000);
    }

    @Test
    @DisplayName("Debe listar el top 10 de rankings")
    void shouldListarTop10_ReturnsRankingDTOList() {
        // Given
        given(rankingRepository.findTop10ByOrderByPuntosDesc()).willReturn(List.of(ranking));
        given(personajeClient.buscarPersonajePorId(10L)).willReturn(personajeDTO);

        // When
        List<RankingDTO> result = rankingService.listarTop10();

        // Then
        assertThat(result).isNotNull().hasSize(1);
        
        then(rankingRepository).should(times(1)).findTop10ByOrderByPuntosDesc();
    }

    @Test
    @DisplayName("Debe buscar un ranking por su ID de manera exitosa")
    void shouldBuscarPorId_WhenRankingExists_ReturnsRankingDTO() {
        // Given
        given(rankingRepository.findById(1L)).willReturn(Optional.of(ranking));
        given(personajeClient.buscarPersonajePorId(10L)).willReturn(personajeDTO);

        // When
        RankingDTO result = rankingService.buscarPorId(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getIdRanking()).isEqualTo(1L);
        assertThat(result.getNombrePersonaje()).isEqualTo("Heroe");

        then(rankingRepository).should(times(1)).findById(1L);
        then(personajeClient).should(times(1)).buscarPersonajePorId(10L);
    }

    @Test
    @DisplayName("Debe lanzar excepción al buscar por ID y no encontrar el ranking")
    void shouldThrowException_WhenBuscarPorIdNotFound() {
        // Given
        given(rankingRepository.findById(99L)).willReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> rankingService.buscarPorId(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Ranking no encontrado");

        then(rankingRepository).should(times(1)).findById(99L);
        then(personajeClient).shouldHaveNoInteractions();
    }
}
