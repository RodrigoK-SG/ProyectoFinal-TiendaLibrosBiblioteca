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

    @Transactional
    public void reducirStockPorVenta(Integer sucursalId, Integer libroId, Integer cantidadComprada) {
        // 1. Armamos la llave compuesta para buscar el registro exacto
        InventarioVentaId id = new InventarioVentaId();
        id.setSucursalId(sucursalId);
        id.setLibroId(libroId);

        // 2. Buscamos el inventario
        InventarioVenta inventario = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No hay registro de inventario para este libro en la sucursal."));

        // 3. Validamos que haya suficiente stock
        if (inventario.getCantidadDisponible() < cantidadComprada) {
            throw new RuntimeException("Stock insuficiente. Solo quedan " + inventario.getCantidadDisponible() + " unidades.");
        }

        // 4. Automatización: Reducimos el stock y guardamos
        inventario.setCantidadDisponible(inventario.getCantidadDisponible() - cantidadComprada);
        inventarioRepository.save(inventario);
    }
}
