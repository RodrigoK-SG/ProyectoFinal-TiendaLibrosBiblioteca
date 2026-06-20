package com.biblioteca.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.ReservaBiblioteca;

public interface ReservaBibliotecaRepositorio extends JpaRepository<ReservaBiblioteca, Integer>{

}
