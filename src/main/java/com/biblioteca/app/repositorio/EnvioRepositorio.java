package com.biblioteca.app.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.Envio;

public interface EnvioRepositorio extends JpaRepository<Envio, Integer>{
	Optional<Envio> findByPedidoId(Integer pedidoId);
}
