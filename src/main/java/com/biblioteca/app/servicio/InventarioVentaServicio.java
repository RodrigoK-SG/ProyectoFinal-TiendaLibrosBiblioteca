package com.biblioteca.app.servicio;

import com.biblioteca.app.modelo.StockLibroDTO;
import com.biblioteca.app.modelo.InventarioVenta;
import com.biblioteca.app.repositorio.InventarioVentaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventarioVentaServicio {

    private final InventarioVentaRepositorio inventarioRepository;

    @Transactional(readOnly = true)
    public InventarioVenta consultarStock(Integer sucursalId, Integer libroId) {
        return inventarioRepository.findByIdSucursalIdAndIdLibroId(sucursalId, libroId)
                .orElseThrow(() -> new RuntimeException("No hay registro de inventario para este libro en la sucursal indicada."));
    }
    
    @Transactional
    public void reducirStockPorVenta(Integer sucursalId, Integer libroId, int cantidadComprada) {
        // 1. Reutilizamos el método de consulta para no repetir la búsqueda ni la excepción
        InventarioVenta inventario = consultarStock(sucursalId, libroId);

        // 2. Validamos que haya suficiente stock con el mensaje detallado
        if (inventario.getCantidadDisponible() < cantidadComprada) {
            throw new RuntimeException("Stock insuficiente. Solo quedan " + inventario.getCantidadDisponible() + " unidades.");
        }

        // 3. Reducimos el stock y guardamos
        inventario.setCantidadDisponible(inventario.getCantidadDisponible() - cantidadComprada);
        inventarioRepository.save(inventario);
    }
    
    @Transactional
    public InventarioVenta actualizarInventario(InventarioVenta inventario) {
        return inventarioRepository.save(inventario);
    }

    // --- NUEVO MÉTODO PARA LA VISTA DE STOCK GENERAL ---
 // ... Tus métodos anteriores ...

    @Transactional(readOnly = true)
    public List<StockLibroDTO> listarStockGeneral() {
        List<InventarioVenta> inventarios = inventarioRepository.findAll();
        
        return inventarios.stream().map(inv -> {
            String estado;
            int disp = inv.getCantidadDisponible();
            int min = (inv.getStockMinimo() != null) ? inv.getStockMinimo() : 5;
            
            if (disp <= 0) {
                estado = "AGOTADO";
            } else if (disp <= min) {
                estado = "BAJO";
            } else if (disp < (min * 3)) {
                estado = "SUFICIENTE";
            } else {
                estado = "ALTO";
            }
            
            return new StockLibroDTO(
                inv.getLibro().getTitulo(),
                inv.getLibro().getIsbn(),
                inv.getLibro().getSlug(),
                inv.getLibro().getEditorial() != null ? inv.getLibro().getEditorial().getNombre() : "Sin Editorial",
                inv.getLibro().getPrecioVentaActual(),
                disp,
                estado,
                inv.getLibro().getActivo(),
                min // <-- AÑADIDO AQUÍ
            );
        }).collect(Collectors.toList());
    }

    // --- NUEVO MÉTODO PARA LA VISTA DE ALERTAS ---
    @Transactional(readOnly = true)
    public List<StockLibroDTO> listarAlertasStock() {
        // Obtenemos todo el stock, pero lo filtramos para quedarnos SOLO con las alertas
        return listarStockGeneral().stream()
                .filter(dto -> dto.getEstadoStock().equals("BAJO") || dto.getEstadoStock().equals("AGOTADO"))
                .collect(Collectors.toList());
    }
}