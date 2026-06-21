package com.biblioteca.app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.Pago;

public interface PagoRepositorio extends JpaRepository<Pago, Integer> {
	List<Pago> findByPedidoId(Integer pedidoId);
}
