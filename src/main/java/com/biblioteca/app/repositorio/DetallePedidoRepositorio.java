package com.biblioteca.app.repositorio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biblioteca.app.modelo.DetallePedido;

public interface DetallePedidoRepositorio extends JpaRepository<DetallePedido, Integer>{
	List<DetallePedido> findByPedidoId(Integer pedidoId);
	@Query("SELECT COALESCE(SUM(dp.cantidad), 0) FROM DetallePedido dp JOIN dp.pedido p WHERE p.estadoActual NOT IN ('CANCELADO', 'PENDIENTE_PAGO') AND p.fechaPedido BETWEEN :inicio AND :fin")
	Integer sumarLibrosVendidosPorFecha(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}
