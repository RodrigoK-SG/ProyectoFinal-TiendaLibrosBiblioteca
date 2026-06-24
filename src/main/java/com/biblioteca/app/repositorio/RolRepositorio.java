package com.biblioteca.app.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.Rol;


public interface RolRepositorio extends JpaRepository<Rol, Integer>{
	boolean existsByNombre(String nombre);
	Optional<Rol> findByNombre(String nombre);
}
