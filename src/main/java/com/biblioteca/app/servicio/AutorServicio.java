package com.biblioteca.app.servicio;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.app.modelo.Autor;
import com.biblioteca.app.repositorio.AutorRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutorServicio {

	private final AutorRepositorio autorRepositorio;
	
	@Transactional(readOnly = true)
    public List<Autor> listarTodas() {
        return autorRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Autor buscarPorId(Integer id) {
        return autorRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: El Autor con ID " + id + " no existe."));
    }

    @Transactional
    public Autor guardar(Autor autor) {
        if (autor.getId() == null && autorRepositorio.existsByNombre(autor.getNombre())) {
            throw new RuntimeException("Operación denegada: Ya existe un Autor Lamado" + autor.getNombre() + "'.");
        }
        return autorRepositorio.save(autor);
    }

    @Transactional
    public void eliminar(Integer id) {
    	Autor autor = buscarPorId(id);
        autorRepositorio.delete(autor);
    }
}
