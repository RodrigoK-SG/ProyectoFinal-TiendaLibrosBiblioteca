package com.biblioteca.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.InventarioVenta;
import com.biblioteca.app.modelo.InventarioVentaId;

public interface InventarioVentaRepositorio extends JpaRepository<InventarioVenta, InventarioVentaId>{

}
