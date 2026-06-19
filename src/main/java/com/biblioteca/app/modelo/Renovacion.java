package com.biblioteca.app.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "RENOVACION")
@Getter @Setter @NoArgsConstructor
public class Renovacion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "PRESTAMO_ID", nullable = false) 
    private Prestamo prestamo;
    
    @Column(name = "NUEVA_FECHA_DEVOLUCION", nullable = false) 
    private LocalDateTime nuevaFechaDevolucion;
}
