package com.biblioteca.app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.Pedido;

public interface PedidoRepositorio extends JpaRepository<Pedido, Integer>{
	List<Pedido> findByClienteId(Integer clienteId);
}
