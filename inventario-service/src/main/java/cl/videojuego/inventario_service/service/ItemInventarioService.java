package cl.videojuego.inventario_service.service;

import cl.videojuego.inventario_service.client.ArmaClient;
import cl.videojuego.inventario_service.dto.ItemInventarioDTO;
import cl.videojuego.inventario_service.dto.ItemInventarioRegistroDTO;
import cl.videojuego.inventario_service.exception.ReferenciaItemInvalidaException;
import cl.videojuego.inventario_service.mapper.ItemInventarioMapper;
import cl.videojuego.inventario_service.model.Inventario;
import cl.videojuego.inventario_service.model.ItemInventario;
import cl.videojuego.inventario_service.model.TipoItem;
import cl.videojuego.inventario_service.repository.InventarioRepository;
import cl.videojuego.inventario_service.repository.ItemInventarioRepository;
import cl.videojuego.inventario_service.repository.TipoItemRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("null")
@RequiredArgsConstructor
public class ItemInventarioService {

    private final ItemInventarioRepository itemInventarioRepository;
    private final InventarioRepository inventarioRepository;
    private final TipoItemRepository tipoItemRepository;
    private final ArmaClient armaClient;

    public List<ItemInventarioDTO> listarTodos() {
        return itemInventarioRepository.findAll()
                .stream()
                .map(ItemInventarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ItemInventarioDTO registrar(ItemInventarioRegistroDTO dto) {

        Inventario inventario = inventarioRepository.findById(dto.getIdInventario())
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        TipoItem tipoItem = tipoItemRepository.findById(dto.getIdTipoItem())
                .orElseThrow(() -> new RuntimeException("Tipo de item no encontrado"));

        // Obtener el nombre real del ítem según su tipo
        String nombreItemObtenido;
        try {
            switch (tipoItem.getNombreTipo().toUpperCase()) {
                case "ARMA":
                    nombreItemObtenido = armaClient.buscarArmaPorId(dto.getIdReferenciaItem()).getNombreArma();
                    break;
                case "POCION":
                case "ARMADURA":
                    throw new ReferenciaItemInvalidaException(
                            "Tipo de ítem " + tipoItem.getNombreTipo().toUpperCase() + 
                            " aún no soportado: tienda-service no expone un cliente para inventario-service");
                default:
                    throw new ReferenciaItemInvalidaException(
                            "Tipo de item no soportado o desconocido para obtener nombre: " + tipoItem.getNombreTipo());
            }
        } catch (FeignException e) {
            // El Feign falló (ej. 404 Not Found porque el ID no existe en su servicio de origen)
            throw new ReferenciaItemInvalidaException(
                    "No se pudo verificar el item ID " + dto.getIdReferenciaItem() + 
                    " en el microservicio correspondiente a " + tipoItem.getNombreTipo() + 
                    ". Estado de red: " + e.status());
        } // No capturamos Exception genérica: errores internos puros (ej. NullPointer) se propagan y loguean como 500.

        ItemInventario item = new ItemInventario();
        item.setInventario(inventario);
        item.setIdReferenciaItem(dto.getIdReferenciaItem());
        item.setTipoItem(tipoItem);
        item.setCantidad(dto.getCantidad());
        item.setFechaObtencion(LocalDate.now());
        item.setEquipado(dto.getEquipado());
        
        // Guardamos el nombre desnormalizado en la base de datos
        item.setNombreItem(nombreItemObtenido);

        ItemInventario guardado = itemInventarioRepository.save(item);

        return ItemInventarioMapper.toDTO(guardado);
    }

    public List<ItemInventarioDTO> listarPorInventario(Long idInventario) {
        return itemInventarioRepository.findByInventario_IdInventario(idInventario)
                .stream()
                .map(ItemInventarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ItemInventarioDTO> listarPorReferencia(Long idReferencia) {
        return itemInventarioRepository.findByIdReferenciaItem(idReferencia)
                .stream()
                .map(ItemInventarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ItemInventarioDTO> listarEquipados(Boolean equipado) {
        return itemInventarioRepository.findByEquipado(equipado)
                .stream()
                .map(ItemInventarioMapper::toDTO)
                .collect(Collectors.toList());
    }
}