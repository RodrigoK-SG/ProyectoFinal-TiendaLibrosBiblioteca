package com.biblioteca.app.servicio;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.app.modelo.ComprobanteFiscal;
import com.biblioteca.app.modelo.Pago;
import com.biblioteca.app.repositorio.ComprobanteFiscalRepositorio;
import com.biblioteca.app.repositorio.PagoRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagoServicio {

    private final PagoRepositorio pagoRepository;
    private final ComprobanteFiscalRepositorio comprobanteRepository;

    @Transactional(readOnly = true)
    public List<Pago> listarPagos() { 
    	return pagoRepository.findAll(); 
    }

    @Transactional(readOnly = true)
    public List<ComprobanteFiscal> listarComprobantes() { 
    	return comprobanteRepository.findAll(); 
    }
    
    @Transactional
    public Pago registrarPago(Pago pago) {
        return pagoRepository.save(pago);
    }
    
    @Transactional
    public ComprobanteFiscal emitirComprobante(ComprobanteFiscal comprobante) {
        if (comprobanteRepository.existsBySerieAndNumero(comprobante.getSerie(), comprobante.getNumero())) {
            throw new RuntimeException("Error Fiscal: El comprobante " + comprobante.getSerie() + "-" + comprobante.getNumero() + " ya fue emitido.");
        }
        return comprobanteRepository.save(comprobante);
    }
}
