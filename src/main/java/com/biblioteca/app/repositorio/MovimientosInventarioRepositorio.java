package com.biblioteca.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.MovimientosInventario;

public interface MovimientosInventarioRepositorio extends JpaRepository<MovimientosInventario, Integer>{

}
