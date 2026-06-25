package com.biblioteca.app.servicio;

import com.biblioteca.app.modelo.Carrito;
import com.biblioteca.app.modelo.CarritoDetalle;
import com.biblioteca.app.modelo.CarritoDetalleId;
import com.biblioteca.app.modelo.Cliente;
import com.biblioteca.app.modelo.Libro;
import com.biblioteca.app.repositorio.CarritoDetalleRepositorio;
import com.biblioteca.app.repositorio.CarritoRepositorio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarritoServicio {

    private final CarritoRepositorio carritoRepository;
    private final CarritoDetalleRepositorio detalleRepository;

    @Transactional
    public void agregarLibroAlCarrito(Cliente cliente, Libro libro, Integer cantidad) {
        // 1. Buscar si el cliente ya tiene un carrito, si no, se lo creamos en automático
        Carrito carrito = carritoRepository.findByClienteId(cliente.getId())
                .orElseGet(() -> {
                    Carrito nuevoCarrito = new Carrito();
                    nuevoCarrito.setCliente(cliente);
                    return carritoRepository.save(nuevoCarrito);
                });

        // 2. Armar la llave del detalle
        CarritoDetalleId detalleId = new CarritoDetalleId();
        detalleId.setCarritoId(carrito.getId());
        detalleId.setLibroId(libro.getId());

        // 3. Buscar si el libro ya estaba en el carrito
        Optional<CarritoDetalle> detalleExistente = detalleRepository.findById(detalleId);

        if (detalleExistente.isPresent()) {
            // Si ya estaba, solo sumamos la cantidad (Automatización)
            CarritoDetalle detalle = detalleExistente.get();
            detalle.setCantidad(detalle.getCantidad() + cantidad);
            detalleRepository.save(detalle);
        } else {
            // Si es nuevo, lo insertamos
            CarritoDetalle nuevoDetalle = new CarritoDetalle();
            nuevoDetalle.setId(detalleId);
            nuevoDetalle.setCarrito(carrito);
            nuevoDetalle.setLibro(libro);
            nuevoDetalle.setCantidad(cantidad);
            detalleRepository.save(nuevoDetalle);
        }
    }

    @Transactional
    public void vaciarCarrito(Integer clienteId) {
        // Se ejecuta automáticamente después de que el cliente paga su Pedido
        carritoRepository.findByClienteId(clienteId).ifPresent(carrito -> {
            // Borramos todos los detalles amarrados a este carrito
            // (Aquí podrías crear un método en el repo para borrar por CarritoId)
            carritoRepository.delete(carrito); 
        });
    }
    
    @Transactional(readOnly = true)
    public List<CarritoDetalle> obtenerCarritoCompleto(Integer clienteId) {
        // 1. Buscamos el carrito del cliente
        Optional<Carrito> carrito = carritoRepository.findByClienteId(clienteId);
        if (carrito.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        // 2. Buscamos todos los detalles asociados a ese carritoId
        return detalleRepository.findByCarritoId(carrito.get().getId());
    }
    
    @Transactional
    public void eliminarLibroDelCarrito(Integer clienteId, Integer libroId) {
        // 1. Buscamos el carrito del cliente
        carritoRepository.findByClienteId(clienteId).ifPresent(carrito -> {
            // 2. Armamos la llave compuesta que usa tu tabla intermedia
            CarritoDetalleId detalleId = new CarritoDetalleId();
            detalleId.setCarritoId(carrito.getId());
            detalleId.setLibroId(libroId);
            
            // 3. Si existe el detalle con esa llave, lo eliminamos permanentemente
            if (detalleRepository.existsById(detalleId)) {
                detalleRepository.deleteById(detalleId);
            }
        });
    }
}
