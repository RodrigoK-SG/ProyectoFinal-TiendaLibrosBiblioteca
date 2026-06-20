package com.biblioteca.app.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.Carrito;

public interface CarritoRepositorio extends JpaRepository<Carrito, Integer> {
    // Buscar el carrito activo de un cliente
    Optional<Carrito> findByClienteId(Integer clienteId);
}