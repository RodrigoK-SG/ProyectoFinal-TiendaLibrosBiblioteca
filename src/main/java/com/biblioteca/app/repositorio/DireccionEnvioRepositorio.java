package com.biblioteca.app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.DireccionEnvio;

public interface DireccionEnvioRepositorio extends JpaRepository<DireccionEnvio, Integer>{
	List<DireccionEnvio> findByClienteId(Integer idCliente);	
}
