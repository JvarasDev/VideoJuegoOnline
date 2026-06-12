package cl.videojuego.inventario_service.service;

import cl.videojuego.inventario_service.client.ArmaClient;
import cl.videojuego.inventario_service.dto.ArmaDTO;
import cl.videojuego.inventario_service.dto.ItemInventarioDTO;
import cl.videojuego.inventario_service.dto.ItemInventarioRegistroDTO;
import cl.videojuego.inventario_service.mapper.ItemInventarioMapper;
import cl.videojuego.inventario_service.model.Inventario;
import cl.videojuego.inventario_service.model.ItemInventario;
import cl.videojuego.inventario_service.model.TipoItem;
import cl.videojuego.inventario_service.repository.InventarioRepository;
import cl.videojuego.inventario_service.repository.ItemInventarioRepository;
import cl.videojuego.inventario_service.repository.TipoItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemInventarioService {

    private final ItemInventarioRepository itemInventarioRepository;
    private final InventarioRepository inventarioRepository;
    private final TipoItemRepository tipoItemRepository;
    private final ArmaClient armaClient;


    public List<ItemInventarioDTO> listarTodos() {
        return itemInventarioRepository.findAll()
                .stream()
                .map(item -> {
                    ArmaDTO arma = obtenerItemSeguro(item.getIdReferenciaItem());
                    return ItemInventarioMapper.toDTO(item, arma);
                })
                .collect(Collectors.toList());
    }

    public ItemInventarioDTO registrar(ItemInventarioRegistroDTO dto) {

        Inventario inventario = inventarioRepository.findById(dto.getIdInventario())
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        TipoItem tipoItem = tipoItemRepository.findById(dto.getIdTipoItem())
                .orElseThrow(() -> new RuntimeException("Tipo de item no encontrado"));

        ArmaDTO arma = obtenerItemSeguro(dto.getIdReferenciaItem());

        ItemInventario item = new ItemInventario();

        item.setInventario(inventario);
        item.setIdReferenciaItem(dto.getIdReferenciaItem());
        item.setTipoItem(tipoItem);
        item.setCantidad(dto.getCantidad());
        item.setFechaObtencion(LocalDate.now());
        item.setEquipado(dto.getEquipado());

        ItemInventario guardado = itemInventarioRepository.save(item);

        return ItemInventarioMapper.toDTO(guardado, arma);
    }

    public List<ItemInventarioDTO> listarPorInventario(Long idInventario) {
        return itemInventarioRepository.findByInventario_IdInventario(idInventario)
                .stream()
                .map(item -> {
                    ArmaDTO arma = obtenerItemSeguro(item.getIdReferenciaItem());
                    return ItemInventarioMapper.toDTO(item, arma);
                })
                .collect(Collectors.toList());
    }

    public List<ItemInventarioDTO> listarPorReferencia(Long idReferencia) {
        return itemInventarioRepository.findByIdReferenciaItem(idReferencia)
                .stream()
                .map(item -> {
                    ArmaDTO arma = obtenerItemSeguro(item.getIdReferenciaItem());
                    return ItemInventarioMapper.toDTO(item, arma);
                })
                .collect(Collectors.toList());
    }

    public List<ItemInventarioDTO> listarEquipados(Boolean equipado) {
        return itemInventarioRepository.findByEquipado(equipado)
                .stream()
                .map(item -> {
                    ArmaDTO arma = obtenerItemSeguro(item.getIdReferenciaItem());
                    return ItemInventarioMapper.toDTO(item, arma);
                })
                .collect(Collectors.toList());
    }

    private ArmaDTO obtenerItemSeguro(Long idReferenciaItem) {
        try {
            return armaClient.buscarArmaPorId(idReferenciaItem);
        } catch (Exception e) {
            ArmaDTO fallback = new ArmaDTO();
            fallback.setIdArma(idReferenciaItem);
            fallback.setNombreArma("Desconocido temporalmente");
            return fallback;
        }
    }
}