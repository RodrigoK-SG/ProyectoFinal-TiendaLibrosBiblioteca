package com.biblioteca.app.repositorio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biblioteca.app.modelo.Prestamo;

public interface PrestamoRepositorio extends JpaRepository<Prestamo, Integer>{
	List<Prestamo> findByClienteId(Integer clienteId);
	@Query("SELECT COUNT(p) FROM Prestamo p WHERE p.estado = 'EN_CURSO'")
	Integer contarPrestamosActivos();

	// Top 5 de libros más alquilados en un mes específico
	@Query("SELECT l.titulo, COUNT(p.id) as total FROM Prestamo p JOIN p.ejemplar e JOIN e.libro l WHERE p.fechaPrestamo BETWEEN :inicio AND :fin GROUP BY l.id ORDER BY total DESC LIMIT 5")
	List<Object[]> topLibrosAlquilados(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}
