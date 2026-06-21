package com.biblioteca.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.ComprobanteFiscal;

public interface ComprobanteFiscalRepositorio extends JpaRepository<ComprobanteFiscal, Integer>{
	// Validar que no se duplique una factura/boleta
    boolean existsBySerieAndNumero(String serie, String numero);
    ComprobanteFiscal findByPedidoId(Integer pedidoId);
}
