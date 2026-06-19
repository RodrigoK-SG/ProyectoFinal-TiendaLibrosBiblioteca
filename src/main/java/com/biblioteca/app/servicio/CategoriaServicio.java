package com.biblioteca.app.servicio;

import org.springframework.stereotype.Service;

import com.biblioteca.app.modelo.Categoria;
import com.biblioteca.app.repositorio.CategoriaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServicio {

    private final CategoriaRepositorio categoriaRepositorio;

    @Transactional(readOnly = true)
    public List<Categoria> listarTodas() {
        return categoriaRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Categoria buscarPorId(Integer id) {
        return categoriaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: La categoría con ID " + id + " no existe."));
    }

    @Transactional
    public Categoria guardar(Categoria categoria) {
        if (categoria.getId() == null && categoriaRepositorio.existsByNombre(categoria.getNombre())) {
            throw new RuntimeException("Operación denegada: Ya existe una categoría llamada '" + categoria.getNombre() + "'.");
        }
        return categoriaRepositorio.save(categoria);
    }

    @Transactional
    public void eliminar(Integer id) {
        Categoria categoria = buscarPorId(id);
        categoriaRepositorio.delete(categoria);
    }
}
