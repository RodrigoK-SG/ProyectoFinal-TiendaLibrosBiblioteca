package com.biblioteca.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.Institucion;

public interface InstitucionRepositorio extends JpaRepository<Institucion, Integer>{

}
