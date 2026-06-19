package com.biblioteca.app.modelo;

import java.math.BigDecimal;

import com.biblioteca.app.modelo.enums.EstadoPenalizacion;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PENALIZACION")
@Getter @Setter @NoArgsConstructor
public class Penalizacion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "PRESTAMO_ID", nullable = false) 
    private Prestamo prestamo;
    
    @Column(nullable = false, length = 255) 
    private String motivo;
    
    @Column(nullable = false) 
    private BigDecimal monto;
    
    @Enumerated(EnumType.STRING) 
    private EstadoPenalizacion estado;
}
