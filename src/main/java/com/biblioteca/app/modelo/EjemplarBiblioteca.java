package com.biblioteca.app.modelo;

import com.biblioteca.app.modelo.enums.EstadoFisico;
import com.biblioteca.app.modelo.enums.SituacionEjemplar;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "EJEMPLAR_BIBLIOTECA")
@Getter @Setter @NoArgsConstructor
public class EjemplarBiblioteca {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "LIBRO_ID", nullable = false) 
    private Libro libro;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "SUCURSAL_ID", nullable = false) 
    private Sucursal sucursal;
    
    @Column(name = "CODIGO_BARRAS", nullable = false, unique = true, length = 50) 
    private String codigoBarras;
    
    @Enumerated(EnumType.STRING) @Column(name = "ESTADO_FISICO") 
    private EstadoFisico estadoFisico;
    
    @Enumerated(EnumType.STRING) 
    private SituacionEjemplar situacion;
}