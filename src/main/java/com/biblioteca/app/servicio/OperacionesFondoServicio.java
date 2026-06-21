package com.biblioteca.app.servicio;

import com.biblioteca.app.modelo.ResenaLibro;
import com.biblioteca.app.modelo.Envio;
import com.biblioteca.app.modelo.ReservaBiblioteca;
import com.biblioteca.app.repositorio.ResenaLibroRepositorio;
import com.biblioteca.app.repositorio.EnvioRepositorio;
import com.biblioteca.app.repositorio.ReservaBibliotecaRepositorio;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OperacionesFondoServicio {

    private final ResenaLibroRepositorio resenaRepository;
    private final EnvioRepositorio envioRepository;
    private final ReservaBibliotecaRepositorio reservaRepository;

    // ================= RESEÑAS =================
    @Transactional
    public ResenaLibro agregarResena(ResenaLibro resena) {
        // Por seguridad, toda reseña entra apagada hasta que un moderador la lea
        resena.setId(null);
        resena.setAprobadoModerador(false); 
        return resenaRepository.save(resena);
    }

    @Transactional
    public void aprobarResena(Integer resenaId) {
        ResenaLibro resena = resenaRepository.findById(resenaId)
                .orElseThrow(() -> new RuntimeException("Reseña no encontrada"));
        resena.setAprobadoModerador(true);
        resenaRepository.save(resena);
    }

    // ================= ENVÍO =================
    @Transactional
    public Envio generarOrdenEnvio(Envio envio) {
        // Este método será llamado automáticamente por tu PedidoService cuando el estado sea "PAGADO"
        envio.setId(null);
        return envioRepository.save(envio);
    }
    
    @Transactional(readOnly = true)
    public Envio buscarEnvioPorPedido(Integer pedidoId) {
        return envioRepository.findByPedidoId(pedidoId)
                .orElseThrow(() -> new RuntimeException("No se encontró información de envío para este pedido."));
    }

    // ================= RESERVAS =================
    @Transactional
    public ReservaBiblioteca registrarReservaFisica(ReservaBiblioteca reserva) {
        reserva.setId(null);
        return reservaRepository.save(reserva);
    }
    
    @Transactional(readOnly = true)
    public List<ReservaBiblioteca> misReservas(Integer clienteId) {
        return reservaRepository.findByClienteId(clienteId);
    }
}
