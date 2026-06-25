package com.biblioteca.app.servicio;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.biblioteca.app.modelo.Editorial;
import com.biblioteca.app.repositorio.EditorialRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EditorialServicio {

	private final EditorialRepositorio editorialRepositorio;

    @Transactional(readOnly = true)
    public List<Editorial> listarTodas() {
        return editorialRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Editorial buscarPorId(Integer id) {
        return editorialRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: La Editorial con ID " + id + " no existe."));
    }

    @Transactional
    public Editorial guardar(Editorial editorial) {
        if (editorial.getId() == null && editorialRepositorio.existsByNombre(editorial.getNombre())) {
            throw new RuntimeException("Operación denegada: Ya existe una Editorial llamada '" + editorial.getNombre() + "'.");
        }
        return editorialRepositorio.save(editorial);
    }

    @Transactional
    public void eliminar(Integer id) {
    	Editorial editorial = buscarPorId(id);
        editorialRepositorio.delete(editorial);
    }
    
    @Transactional(readOnly = true)
    public Editorial obtenerOAsignarDefault(String nombre) {
        if (nombre == null || nombre.isEmpty()) return buscarPorId(1);
        
        // Asumiendo que tienes este método en tu repositorio:
        return editorialRepositorio.findByNombreIgnoreCase(nombre)
                .orElse(buscarPorId(1));
    }
}
