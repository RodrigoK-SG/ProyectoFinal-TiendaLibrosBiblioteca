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
    public Cotizacion emitirCotizacion(Cotizacion cotizacion) {
        cotizacion.setId(null);
        return cotizacionRepository.save(cotizacion);
    }

    @Transactional
    public Contrato firmarContrato(Contrato contrato) {
        contrato.setId(null);
        return contratoRepository.save(contrato);
    }
}