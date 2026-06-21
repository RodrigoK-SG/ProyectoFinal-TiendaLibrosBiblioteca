package com.biblioteca.app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.Penalizacion;

public interface PenalizacionRepositorio extends JpaRepository<Penalizacion, Integer>{
	List<Penalizacion> findByPrestamoId(Integer prestamoId);
}
