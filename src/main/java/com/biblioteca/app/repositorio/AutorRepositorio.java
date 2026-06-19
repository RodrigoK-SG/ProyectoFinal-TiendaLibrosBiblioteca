package com.biblioteca.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.Autor;

public interface AutorRepositorio extends JpaRepository<Autor, Integer>{
	boolean existsByNombre(String nombre);
}
