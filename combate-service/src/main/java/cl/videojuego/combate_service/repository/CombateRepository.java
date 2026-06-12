package cl.videojuego.combate_service.repository;

import cl.videojuego.combate_service.model.Combate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CombateRepository extends JpaRepository<Combate, Long> {

    List<Combate> findByIdPersonajeAtacante(Long idPersonajeAtacante);

    List<Combate> findByIdPersonajeDefensor(Long idPersonajeDefensor);

    List<Combate> findByIdGanador(Long idGanador);

    List<Combate> findByTipoCombate_IdTipoCombate(Long idTipoCombate);

    List<Combate> findByEstadoCombate_IdEstadoCombate(Long idEstadoCombate);
}