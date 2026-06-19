package com.biblioteca.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.Categoria;


public interface CategoriaRepositorio extends JpaRepository<Categoria, Integer>{
	boolean existsByNombre(String nombre);
}
