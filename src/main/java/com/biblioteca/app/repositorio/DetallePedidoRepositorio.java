package com.biblioteca.app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.DetallePedido;

public interface DetallePedidoRepositorio extends JpaRepository<DetallePedido, Integer>{
	List<DetallePedido> findByPedidoId(Integer pedidoId);
}
