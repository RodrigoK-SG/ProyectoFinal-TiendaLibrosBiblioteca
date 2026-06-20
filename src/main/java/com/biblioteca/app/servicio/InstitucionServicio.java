package com.biblioteca.app.servicio;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.app.modelo.Institucion;
import com.biblioteca.app.repositorio.InstitucionRepositorio;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstitucionServicio {

    private final InstitucionRepositorio institucionRepository;

    @Transactional(readOnly = true)
    public List<Institucion> listarTodas() {
        return institucionRepository.findAll();
    }

    @Transactional
    public Institucion guardar(Institucion institucion) {
        return institucionRepository.save(institucion);
    }
    
    // No hacemos método de eliminar aquí, porque si quieres eliminar una institución, 
    // lo correcto es "desactivar" al Cliente padre.
}
