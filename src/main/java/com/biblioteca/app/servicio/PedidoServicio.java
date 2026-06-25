package com.biblioteca.app.servicio;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.app.modelo.DetallePedido;
import com.biblioteca.app.modelo.HistorialEstadoPedido;
import com.biblioteca.app.modelo.Pedido;
import com.biblioteca.app.modelo.enums.EstadoPedido;
import com.biblioteca.app.repositorio.HistorialEstadoPedidoRepositorio;
import com.biblioteca.app.repositorio.PedidoRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServicio {

    private final PedidoRepositorio pedidoRepository;
    private final HistorialEstadoPedidoRepositorio historialRepository;

    // ==========================================
    // 1. LEER (READ ONLY)
    // ==========================================
    @Transactional(readOnly = true)
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Pedido buscarPorId(Integer id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado."));
    }

    // ==========================================
    // 2. CREAR TRANSACCIÓN (CREATE)
    // ==========================================
    @Transactional
    public Pedido registrarPedido(Pedido pedido) {
        // Aseguramos que sea una inserción limpia (sin ID previo)
        pedido.setId(null);
        
        // Regla de negocio: Todo pedido nace en estado "PENDIENTE"
        pedido.setEstadoActual(EstadoPedido.PENDIENTE_PAGO);

        // Amarramos los detalles al pedido padre para que JPA sepa a quién pertenecen
        if (pedido.getDetalles() != null) {
            for (DetallePedido detalle : pedido.getDetalles()) {
                detalle.setPedido(pedido); 
            }
        }

        // Guardamos el pedido (Por cascada se guardarán sus detalles en DETALLE_PEDIDO)
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // AUDITORÍA: Registramos el primer hito en HISTORIAL_ESTADO_PEDIDO
        HistorialEstadoPedido historial = new HistorialEstadoPedido();
        historial.setPedido(pedidoGuardado);
        historial.setEstado(EstadoPedido.PENDIENTE_PAGO);
        historial.setComentario("Pedido creado en el sistema por el cliente.");
        historialRepository.save(historial);

        return pedidoGuardado;
    }

    // Avanzar el estado es una CREACIÓN de historial, NO una edición del pedido antiguo
    @Transactional
    public void cambiarEstado(Integer pedidoId, EstadoPedido nuevoEstado, String motivo) {
        Pedido pedido = buscarPorId(pedidoId);
        
        // Actualizamos el puntero del estado actual en el pedido
        pedido.setEstadoActual(nuevoEstado);
        pedidoRepository.save(pedido);

        // Insertamos el nuevo renglón histórico (Auditoría pura)
        HistorialEstadoPedido historial = new HistorialEstadoPedido();
        historial.setPedido(pedido);
        historial.setEstado(nuevoEstado);
        historial.setComentario(motivo);
        historialRepository.save(historial);
    }
}
