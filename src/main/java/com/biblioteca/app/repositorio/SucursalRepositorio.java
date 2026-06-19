package com.biblioteca.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.Sucursal;


public interface SucursalRepositorio extends JpaRepository<Sucursal, Integer>{

}
