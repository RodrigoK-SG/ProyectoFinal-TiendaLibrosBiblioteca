package com.biblioteca.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.Prestamo;

public interface PrestamoRepositorio extends JpaRepository<Prestamo, Integer>{
}
