package com.biblioteca.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.CarritoDetalle;
import com.biblioteca.app.modelo.CarritoDetalleId;

public interface CarritoDetalleRepositorio extends JpaRepository<CarritoDetalle, CarritoDetalleId> {
}
