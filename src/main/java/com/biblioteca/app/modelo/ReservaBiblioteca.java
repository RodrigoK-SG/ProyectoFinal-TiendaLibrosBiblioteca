package com.biblioteca.app.modelo;

import jakarta.persistence.Entity;

import com.biblioteca.app.modelo.enums.EstadoReserva;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "RESERVA_BIBLIOTECA")
@Getter @Setter @NoArgsConstructor
public class ReservaBiblioteca {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "LIBRO_ID", nullable = false) 
    private Libro libro;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "CLIENTE_ID", nullable = false) 
    private Cliente cliente;
    
    @Enumerated(EnumType.STRING) 
    private EstadoReserva estado;
}
