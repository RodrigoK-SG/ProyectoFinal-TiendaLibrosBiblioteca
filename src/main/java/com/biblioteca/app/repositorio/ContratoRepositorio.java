package com.biblioteca.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.Contrato;

public interface ContratoRepositorio extends JpaRepository<Contrato, Integer>{

}
