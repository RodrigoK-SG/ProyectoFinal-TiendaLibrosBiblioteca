package com.biblioteca.app.servicio;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.app.modelo.Sucursal;
import com.biblioteca.app.repositorio.SucursalRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SucursalServicio {
    private final SucursalRepositorio sucursalRepository;

    @Transactional(readOnly = true)
    public List<Sucursal> listarTodas() {
        return sucursalRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Sucursal buscarPorId(Integer id) {
        return sucursalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
    }

    @Transactional
    public Sucursal guardar(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    @Transactional
    public void eliminar(Integer id) {
        // Borrado Físico
        sucursalRepository.deleteById(id);
    }
}
