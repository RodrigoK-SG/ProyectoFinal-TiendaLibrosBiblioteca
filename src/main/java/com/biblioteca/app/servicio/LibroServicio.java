package com.biblioteca.app.servicio;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.app.modelo.Libro;
import com.biblioteca.app.repositorio.LibroRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LibroServicio {

	private final LibroRepositorio libroRepository;

    @Transactional(readOnly = true)
    public List<Libro> listarTodosLosLibros() {
        return libroRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Libro> listarLibrosParaTienda() {
        return libroRepository.findByActivoTrue();
    }

    @Transactional(readOnly = true)
    public Libro buscarPorId(Integer id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: El libro con ID " + id + " no existe."));
    }

    @Transactional
    public Libro guardar(Libro libro) {
        // REGLA 1: Si es un libro NUEVO, verificamos que el ISBN no exista
        if (libro.getId() == null) {
            if (libroRepository.existsByIsbn(libro.getIsbn())) {
                throw new RuntimeException("Operación denegada: Ya existe un libro con el ISBN " + libro.getIsbn());
            }
            if (libro.getSlug() != null && libroRepository.existsBySlug(libro.getSlug())) {
                throw new RuntimeException("Operación denegada: La URL (slug) ya está en uso.");
            }
            libro.setActivo(true);
        }
        
        return libroRepository.save(libro);
    }

    @Transactional
    public void eliminar(Integer id) {
        Libro libro = buscarPorId(id);
        // ¡ATENCIÓN AQUÍ!
        // NUNCA usamos libroRepository.delete(libro);
        // Si borramos el libro físicamente, los recibos de ventas pasadas de este libro darán error y crashearán el sistema.
        // En su lugar, simplemente lo "apagamos" para que ya no salga en la tienda.
        libro.setActivo(false);
        libroRepository.save(libro);
    }
    
    // Método extra para volver a habilitarlo si nos equivocamos al borrarlo
    @Transactional
    public void reactivar(Integer id) {
        Libro libro = buscarPorId(id);
        libro.setActivo(true);
        libroRepository.save(libro);
    }
}
