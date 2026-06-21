package com.biblioteca.app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.MovimientosInventario;

public interface MovimientosInventarioRepositorio extends JpaRepository<MovimientosInventario, Integer>{
	List<MovimientosInventario> findByLibroIdAndSucursalIdOrderByIdDesc(Integer libroId, Integer sucursalId);
}
