package com.biblioteca.app.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.app.modelo.InventarioVenta;
import com.biblioteca.app.modelo.InventarioVentaId;
import com.biblioteca.app.modelo.MovimientosInventario;
import com.biblioteca.app.repositorio.InventarioVentaRepositorio;
import com.biblioteca.app.repositorio.MovimientosInventarioRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimientosInventarioServicio {

    private final MovimientosInventarioRepositorio movimientosRepository;
    private final InventarioVentaRepositorio inventarioRepository;
    
    @Transactional(readOnly = true)
    public List<MovimientosInventario> listarTodosLosMovimientos() {
        return movimientosRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<MovimientosInventario> consultarKardex(Integer libroId, Integer sucursalId) {
        return movimientosRepository.findByLibroIdAndSucursalIdOrderByIdDesc(libroId, sucursalId);
    }

    @Transactional
    public void registrarMovimiento(MovimientosInventario movimiento) {
        
        // 1. Extraemos los datos clave del movimiento
        Integer sucursalId = movimiento.getSucursal().getId();
        Integer libroId = movimiento.getLibro().getId();
        int cantidad = movimiento.getCantidad();
        String tipo = movimiento.getTipoMovimiento().name();

        // 2. Determinamos matemáticamente qué hará el movimiento
        boolean esEntrada = tipo.equals("INGRESO_PROVEEDOR") || tipo.equals("DEVOLUCION") || tipo.equals("TRASLADO_ENTRADA") || tipo.equals("AJUSTE_MANUAL");
        boolean esSalida = tipo.equals("VENTA_FISICA") || tipo.equals("VENTA_ONLINE") || tipo.equals("MERMA") || tipo.equals("TRASLADO_SALIDA") || tipo.equals("DESCARTE_BIBLIOTECA");

        // 3. Buscamos el stock actual de ese libro en esa sucursal
        Optional<InventarioVenta> invOpt = inventarioRepository.findByIdSucursalIdAndIdLibroId(sucursalId, libroId);
        InventarioVenta inventario;

        if (invOpt.isPresent()) {
            inventario = invOpt.get();
        } else {
            // Si el libro no existe en la tabla INVENTARIO_VENTA y quieren hacer una SALIDA, bloqueamos.
            if (esSalida) {
                throw new RuntimeException("Error: No se puede registrar una salida. El libro no tiene stock inicial registrado.");
            }
            
            // Si es un libro NUEVO (Entrada), le creamos su registro de inventario desde cero
            inventario = new InventarioVenta();
            
            // Instanciamos tu llave primaria compuesta
            InventarioVentaId invId = new InventarioVentaId();
            invId.setSucursalId(sucursalId);
            invId.setLibroId(libroId);
            
            inventario.setId(invId);
            inventario.setSucursal(movimiento.getSucursal());
            inventario.setLibro(movimiento.getLibro());
            inventario.setCantidadDisponible(0); // Empezamos en 0 antes de sumar
            inventario.setCantidadReservada(0);
            inventario.setStockMinimo(5);
            inventario.setStockMaximo(1000);
        }

        // 4. Aplicamos la suma o resta a la cantidad disponible
        if (esEntrada) {
            inventario.setCantidadDisponible(inventario.getCantidadDisponible() + cantidad);
        } else if (esSalida) {
            // Validamos que no queden números negativos (violaría tu CHECK de Base de Datos)
            if (inventario.getCantidadDisponible() < cantidad) {
                throw new RuntimeException("Stock insuficiente. Intentas sacar " + cantidad + " unidades pero solo hay " + inventario.getCantidadDisponible() + ".");
            }
            inventario.setCantidadDisponible(inventario.getCantidadDisponible() - cantidad);
        }

        // 5. Guardamos TODO en la base de datos (El Historial y el Inventario Actualizado)
        inventarioRepository.save(inventario);
        movimientosRepository.save(movimiento);
    }
}