package com.biblioteca.app.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.biblioteca.app.modelo.enums.EstadoPrestamo;
import com.biblioteca.app.modelo.enums.TipoPrestamo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PRESTAMO")
@Getter @Setter @NoArgsConstructor
public class Prestamo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "EJEMPLAR_ID", nullable = false) 
    private EjemplarBiblioteca ejemplar;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "CLIENTE_ID", nullable = false) 
    private Cliente cliente;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "USUARIO_BIBLIOTECARIO_ID", nullable = false)
    private Usuario bibliotecario;
    
    @Enumerated(EnumType.STRING) @Column(name = "TIPO_PRESTAMO", nullable = false) 
    private TipoPrestamo tipoPrestamo;
    
    @Column(name = "FECHA_DEVOLUCION_ESPERADA", nullable = false) 
    private LocalDateTime fechaDevolucionEsperada;
    
    @Column(name = "FECHA_DEVOLUCION_REAL") 
    private LocalDateTime fechaDevolucionReal;
    
    @Column(name = "COSTO_ALQUILER") 
    private BigDecimal costoAlquiler;
    
    @Enumerated(EnumType.STRING) 
    private EstadoPrestamo estado;
}