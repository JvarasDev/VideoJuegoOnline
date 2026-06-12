package cl.videojuego.personaje_service.repository;

import cl.videojuego.personaje_service.model.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonajeRepository extends JpaRepository<Personaje, Long> {

    List<Personaje> findByIdUsuario(Long idUsuario);

    List<Personaje> findByNivel(Integer nivel);

    List<Personaje> findByClasePersonaje_IdClasePersonaje(Long idClasePersonaje);

    List<Personaje> findByEstadoPersonaje_IdEstadoPersonaje(Long idEstadoPersonaje);
}


