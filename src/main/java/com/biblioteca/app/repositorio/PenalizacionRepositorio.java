package com.biblioteca.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.Penalizacion;

public interface PenalizacionRepositorio extends JpaRepository<Penalizacion, Integer>{

}
