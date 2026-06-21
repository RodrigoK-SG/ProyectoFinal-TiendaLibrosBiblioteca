package com.biblioteca.app.servicio;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.app.modelo.MovimientosInventario;
import com.biblioteca.app.repositorio.MovimientosInventarioRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimientosInventarioServicio {

    private final MovimientosInventarioRepositorio movimientosRepository;

    @Transactional(readOnly = true)
    public List<MovimientosInventario> consultarKardex(Integer libroId, Integer sucursalId) {
        return movimientosRepository.findByLibroIdAndSucursalIdOrderByIdDesc(libroId, sucursalId);
    }

    @Transactional
    public MovimientosInventario registrarMovimiento(MovimientosInventario movimiento) {
        movimiento.setId(null);
        
        // Aquí se inyectará la lógica del Grupo 3 (Automatizados):
        // Al guardar este movimiento, se debe actualizar la tabla INVENTARIO_VENTA sumando o restando el stock.
        return movimientosRepository.save(movimiento);
    }
}
