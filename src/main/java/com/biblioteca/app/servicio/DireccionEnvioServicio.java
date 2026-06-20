package com.biblioteca.app.servicio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.app.modelo.DireccionEnvio;
import com.biblioteca.app.repositorio.DireccionEnvioRepositorio;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DireccionEnvioServicio {

    private final DireccionEnvioRepositorio direccionRepository;

    @Transactional(readOnly = true)
    public List<DireccionEnvio> listarDireccionesPorCliente(Integer idCliente) {
        // En el futuro agregaremos este método en el Repositorio para buscar 
        // solo las direcciones de un cliente específico.
        return direccionRepository.findByClienteId(idCliente);
    }

    @Transactional
    public DireccionEnvio buscarPorId(Integer id) {
        return direccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));
    }

    @Transactional
    public DireccionEnvio guardar(DireccionEnvio direccion) {
        return direccionRepository.save(direccion);
    }

    @Transactional
    public void eliminar(Integer direccionId) {
        // A diferencia del cliente, una dirección física sí se puede borrar físicamente 
        // de la base de datos si el cliente ya no vive ahí y no está atada a un pedido histórico.
        direccionRepository.deleteById(direccionId);
    }
}