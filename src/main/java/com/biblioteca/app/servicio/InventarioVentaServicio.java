package com.biblioteca.app.servicio;

import com.biblioteca.app.modelo.InventarioVenta;
import com.biblioteca.app.modelo.InventarioVentaId;
import com.biblioteca.app.repositorio.InventarioVentaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
