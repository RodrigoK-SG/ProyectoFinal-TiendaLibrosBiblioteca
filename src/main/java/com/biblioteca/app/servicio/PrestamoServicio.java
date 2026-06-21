package com.biblioteca.app.servicio;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.app.modelo.Penalizacion;
import com.biblioteca.app.modelo.Prestamo;
import com.biblioteca.app.modelo.Renovacion;
import com.biblioteca.app.repositorio.PenalizacionRepositorio;
import com.biblioteca.app.repositorio.PrestamoRepositorio;
import com.biblioteca.app.repositorio.RenovacionRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrestamoServicio {

    private final PrestamoRepositorio prestamoRepository;
    private final RenovacionRepositorio renovacionRepository;
    private final PenalizacionRepositorio penalizacionRepository;

    @Transactional(readOnly = true)
    public List<Prestamo> historialDePrestamos() { 
    	return prestamoRepository.findAll(); 
    }

    @Transactional
    public Prestamo registrarPrestamo(Prestamo prestamo) {
        prestamo.setId(null);
        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Renovacion solicitarRenovacion(Renovacion renovacion) {
        renovacion.setId(null);
        return renovacionRepository.save(renovacion);
    }

    @Transactional
    public Penalizacion aplicarPenalizacion(Penalizacion penalizacion) {
        penalizacion.setId(null);
        return penalizacionRepository.save(penalizacion);
    }
    
    @Transactional(readOnly = true)
    public Prestamo buscarPrestamoPorId(Integer id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado."));
    }
}
