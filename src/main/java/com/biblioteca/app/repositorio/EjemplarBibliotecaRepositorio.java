package com.biblioteca.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.EjemplarBiblioteca;

public interface EjemplarBibliotecaRepositorio extends JpaRepository<EjemplarBiblioteca, Integer>{
	boolean existsByCodigoBarras(String codigoBarras);
}
