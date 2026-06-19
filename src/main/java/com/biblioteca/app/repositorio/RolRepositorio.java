package com.biblioteca.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.Rol;


public interface RolRepositorio extends JpaRepository<Rol, Integer>{
	
}
