package com.biblioteca.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Integer>{
	boolean existsByNumeroDocumento(String numeroDocumento);
}
