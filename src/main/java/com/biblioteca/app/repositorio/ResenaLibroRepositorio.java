package com.biblioteca.app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.ResenaLibro;

public interface ResenaLibroRepositorio extends JpaRepository<ResenaLibro, Integer>{
	List<ResenaLibro> findByLibroId(Integer libroId);
	}
