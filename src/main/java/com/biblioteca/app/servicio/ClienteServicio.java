package com.biblioteca.app.servicio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.biblioteca.app.modelo.Cliente;
import com.biblioteca.app.repositorio.ClienteRepositorio;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServicio {

    private final ClienteRepositorio clienteRepository;

    // ================= CLIENTE (CRUD) =================

    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Cliente buscarPorId(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado."));
    }

    @Transactional
    public Cliente guardar(Cliente cliente) {
        // Validar que el DNI/RUC no se repita al crear
        if (cliente.getId() == null && clienteRepository.existsByNumeroDocumento(cliente.getNumeroDocumento())) {
            throw new RuntimeException("El número de documento ya está registrado.");
        }
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void eliminar(Integer id) {
        // Borrado Lógico
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        cliente.setActivo(false);
        clienteRepository.save(cliente);
    }
    
    
}