package com.biblioteca.app.servicio;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.app.modelo.Contrato;
import com.biblioteca.app.modelo.Cotizacion;
import com.biblioteca.app.repositorio.ContratoRepositorio;
import com.biblioteca.app.repositorio.CotizacionRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContratoServicio {

    private final ContratoRepositorio contratoRepository;
    private final CotizacionRepositorio cotizacionRepository;

    @Transactional(readOnly = true)
    public List<Cotizacion> listarCotizaciones() { 
    	return cotizacionRepository.findAll(); 
    }

    @Transactional(readOnly = true)
    public List<Contrato> listarContratos() { 
    	return contratoRepository.findAll(); 
    }

    @Transactional
    public Contrato registrarContrato(Contrato contrato) {
        if (contratoRepository.existsByCodigoContrato(contrato.getCodigoContrato())) {
            throw new RuntimeException("El código de contrato '" + contrato.getCodigoContrato() + "' ya está registrado.");
        }
        return contratoRepository.save(contrato);
    }

    @Transactional(readOnly = true)
    public Contrato buscarContratoPorId(Integer id) {
        return contratoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato no encontrado."));
    }

    @Transactional
    public Cotizacion registrarCotizacion(Cotizacion cotizacion) {
        return cotizacionRepository.save(cotizacion);
    }
    
    @Transactional(readOnly = true)
    public Cotizacion buscarCotizacionPorId(Integer id) {
        return cotizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada."));
    }
}