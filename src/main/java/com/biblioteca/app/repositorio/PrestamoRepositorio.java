package com.biblioteca.app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.Prestamo;

public interface PrestamoRepositorio extends JpaRepository<Prestamo, Integer>{
	List<Prestamo> findByClienteId(Integer clienteId);
}
