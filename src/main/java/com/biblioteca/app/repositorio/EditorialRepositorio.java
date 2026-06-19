package com.biblioteca.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.Editorial;


public interface EditorialRepositorio extends JpaRepository<Editorial, Integer>{
	boolean existsByNombre(String nombre);
}
