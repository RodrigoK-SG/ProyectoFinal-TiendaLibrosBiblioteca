package com.biblioteca.app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.HistorialEstadoPedido;

public interface HistorialEstadoPedidoRepositorio extends JpaRepository<HistorialEstadoPedido, Integer>{
	List<HistorialEstadoPedido> findByPedidoIdOrderByIdAsc(Integer pedidoId);
}
