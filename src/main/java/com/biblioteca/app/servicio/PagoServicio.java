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
    public List<Pago> listarPagos() { return pagoRepository.findAll(); }

    @Transactional(readOnly = true)
    public List<ComprobanteFiscal> listarComprobantes() { return comprobanteRepository.findAll(); }

    @Transactional
    public Pago procesarPago(Pago pago, boolean requiereFactura) {
        pago.setId(null);
        Pago pagoRegistrado = pagoRepository.save(pago);

        if (requiereFactura) {
            ComprobanteFiscal comprobante = new ComprobanteFiscal();
            comprobante.setPago(pagoRegistrado);
            
            // Usamos TUS métodos originales: setSerie() y setNumero()
            comprobante.setSerie("F001");
            comprobante.setNumero(System.currentTimeMillis() + ""); 
            
            // Faltaría llenar los otros campos obligatorios (monto total, impuesto, etc.)
            // que podrías extraer del objeto "pago"
            
            comprobanteRepository.save(comprobante);
        }

        return pagoRegistrado;
    }
}
