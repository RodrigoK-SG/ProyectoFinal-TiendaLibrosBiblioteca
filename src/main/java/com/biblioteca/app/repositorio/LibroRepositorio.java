package com.biblioteca.app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.Libro;


public interface LibroRepositorio extends JpaRepository<Libro, Integer>{
	
	//Validaciones de existencia
	boolean existsByIsbn(String isbn);
	boolean existsBySlug(String slug);
	
	List<Libro> findByActivoTrue();
}
