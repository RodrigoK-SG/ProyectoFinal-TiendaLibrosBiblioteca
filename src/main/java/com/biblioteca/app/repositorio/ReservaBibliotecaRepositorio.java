package com.biblioteca.app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.app.modelo.ReservaBiblioteca;
import com.biblioteca.app.modelo.enums.EstadoReserva;

public interface ReservaBibliotecaRepositorio extends JpaRepository<ReservaBiblioteca, Integer>{
	List<ReservaBiblioteca> findByClienteId(Integer clienteId);
    List<ReservaBiblioteca> findByLibroIdAndEstado(Integer libroId, EstadoReserva estado);
}
