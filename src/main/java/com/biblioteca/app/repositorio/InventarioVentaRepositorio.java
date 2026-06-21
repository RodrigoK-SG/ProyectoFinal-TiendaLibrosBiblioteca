package com.biblioteca.app.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.InventarioVenta;
import com.biblioteca.app.modelo.InventarioVentaId;

public interface InventarioVentaRepositorio extends JpaRepository<InventarioVenta, InventarioVentaId>{
	// Buscar el stock de un libro en una sucursal específica
    Optional<InventarioVenta> findByIdSucursalIdAndIdLibroId(Integer sucursalId, Integer libroId);
    // Buscar todo el stock de una sucursal
    List<InventarioVenta> findByIdSucursalId(Integer sucursalId);
}
