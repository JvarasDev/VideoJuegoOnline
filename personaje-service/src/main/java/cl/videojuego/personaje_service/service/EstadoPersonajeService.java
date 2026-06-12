package cl.videojuego.personaje_service.service;

import cl.videojuego.personaje_service.model.EstadoPersonaje;
import cl.videojuego.personaje_service.repository.EstadoPersonajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadoPersonajeService {

    private final EstadoPersonajeRepository estadoPersonajeRepository;


    public List<EstadoPersonaje> listarEstados() {
        return estadoPersonajeRepository.findAll();
    }
}
