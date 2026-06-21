package com.biblioteca.app.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Integer>{
	boolean existsByNumeroDocumento(String numeroDocumento);
	List<Cliente> findByActivoTrue();
	Optional<Cliente> findByUsuarioId(Integer usuarioId);
}
