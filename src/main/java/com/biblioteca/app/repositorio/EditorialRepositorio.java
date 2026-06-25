package com.biblioteca.app.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.Editorial;


public interface EditorialRepositorio extends JpaRepository<Editorial, Integer>{
	boolean existsByNombre(String nombre);

	Optional<Editorial> findByNombreIgnoreCase(String nombre);
}
