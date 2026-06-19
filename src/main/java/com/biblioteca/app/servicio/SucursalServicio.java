package com.biblioteca.app.servicio;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.app.modelo.Sucursal;
import com.biblioteca.app.repositorio.SucursalRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SucursalServicio {
    private final SucursalRepositorio sucursalRepository;

    public List<Sucursal> listarTodas() { 
    	return sucursalRepository.findAll(); 
    }
    
    @Transactional
    public Sucursal guardar(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }
}
