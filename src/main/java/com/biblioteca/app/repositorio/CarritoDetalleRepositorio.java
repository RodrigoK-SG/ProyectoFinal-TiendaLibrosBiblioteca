package com.biblioteca.app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.CarritoDetalle;
import com.biblioteca.app.modelo.CarritoDetalleId;

public interface CarritoDetalleRepositorio extends JpaRepository<CarritoDetalle, CarritoDetalleId> {
	List<CarritoDetalle> findByCarritoId(Integer carritoId);
    void deleteByCarritoId(Integer carritoId); // Para vaciar el carrito rápido
}
