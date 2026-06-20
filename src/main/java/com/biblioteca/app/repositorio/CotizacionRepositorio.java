package com.biblioteca.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.Cotizacion;

public interface CotizacionRepositorio extends JpaRepository<Cotizacion, Integer>{

}
