package com.biblioteca.app.servicio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.app.modelo.EjemplarBiblioteca;
import com.biblioteca.app.modelo.enums.EstadoFisico;
import com.biblioteca.app.modelo.enums.SituacionEjemplar;
import com.biblioteca.app.repositorio.EjemplarBibliotecaRepositorio;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EjemplarBibliotecaServicio {

    private final EjemplarBibliotecaRepositorio ejemplarRepository;

    @Transactional(readOnly = true)
    public List<EjemplarBiblioteca> listarTodos() {
        return ejemplarRepository.findAll();
    }

    @Transactional
    public EjemplarBiblioteca guardar(EjemplarBiblioteca ejemplar) {
        // Al registrar un libro físico por primera vez, siempre entra "NUEVO" y "DISPONIBLE"
        if (ejemplar.getId() == null) {
            ejemplar.setEstadoFisico(EstadoFisico.NUEVO);
            ejemplar.setSituacion(SituacionEjemplar.DISPONIBLE);
            
            // Validar código de barras único
            if (ejemplarRepository.existsByCodigoBarras(ejemplar.getCodigoBarras())) {
                throw new RuntimeException("El código de barras ya existe en el sistema.");
            }
        }
        return ejemplarRepository.save(ejemplar);
    }

    // En lugar de "eliminar", en una biblioteca los ejemplares se "Descartan" o se reportan "Extraviados"
    @Transactional
    public void reportarExtraviado(Integer id) {
        EjemplarBiblioteca ejemplar = ejemplarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ejemplar no encontrado"));
        
        ejemplar.setSituacion(SituacionEjemplar.EXTRAVIADO);
        ejemplarRepository.save(ejemplar);
    }

    @Transactional
    public void darDeBajaPorDeterioro(Integer id) {
        EjemplarBiblioteca ejemplar = ejemplarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ejemplar no encontrado"));
        
        ejemplar.setEstadoFisico(EstadoFisico.PARA_DESCARTE);
        ejemplarRepository.save(ejemplar);
    }
}
