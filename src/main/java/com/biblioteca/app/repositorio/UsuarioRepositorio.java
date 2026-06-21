package com.biblioteca.app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.Usuario;


public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer>{
	boolean existsByEmail(String email);
	List<Usuario> findByActivoTrue();
}
