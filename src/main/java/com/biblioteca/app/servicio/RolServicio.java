package com.biblioteca.app.servicio;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.app.modelo.Rol;
import com.biblioteca.app.repositorio.RolRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolServicio {
    private final RolRepositorio rolRepositorio;

    public List<Rol> listarTodos() { 
    	return rolRepositorio.findAll(); 
    }
    
    @Transactional
    public Rol guardar(Rol rol) {
        return rolRepositorio.save(rol);
    }
}