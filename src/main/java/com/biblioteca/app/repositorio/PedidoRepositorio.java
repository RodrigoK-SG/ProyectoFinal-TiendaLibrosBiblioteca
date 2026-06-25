package com.biblioteca.app.repositorio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biblioteca.app.modelo.Pedido;
import com.biblioteca.app.modelo.enums.CanalVenta;

public interface PedidoRepositorio extends JpaRepository<Pedido, Integer>{
	List<Pedido> findByClienteId(Integer clienteId);
	@Query("SELECT COALESCE(SUM(p.total), 0) FROM Pedido p WHERE p.estadoActual NOT IN ('CANCELADO', 'PENDIENTE_PAGO') AND p.fechaPedido BETWEEN :inicio AND :fin")
	BigDecimal sumarIngresosPorFecha(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

	@Query("SELECT COALESCE(SUM(p.total), 0) FROM Pedido p WHERE p.canalVenta = :canal AND p.estadoActual NOT IN ('CANCELADO', 'PENDIENTE_PAGO') AND p.fechaPedido BETWEEN :inicio AND :fin")
	BigDecimal sumarIngresosPorCanalYFecha(@Param("canal") CanalVenta canalVenta, @Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}
