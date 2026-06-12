package cl.videojuego.personaje_service.service;

import cl.videojuego.personaje_service.model.ClasePersonaje;
import cl.videojuego.personaje_service.repository.ClasePersonajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClasePersonajeService {

    private final ClasePersonajeRepository clasePersonajeRepository;


    public List<ClasePersonaje> listarClases() {
        return clasePersonajeRepository.findAll();
    }
}