package com.biblioteca.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.Pedido;

public interface PedidoRepositorio extends JpaRepository<Pedido, Integer>{

}
