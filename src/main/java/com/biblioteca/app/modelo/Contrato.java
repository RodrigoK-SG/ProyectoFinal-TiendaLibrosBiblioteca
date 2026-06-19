package com.biblioteca.app.modelo;

import java.math.BigDecimal;

import com.biblioteca.app.modelo.enums.EstadoContrato;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CONTRATO")
@Getter @Setter @NoArgsConstructor
public class Contrato {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "INSTITUCION_ID", nullable = false) 
    private Institucion institucion;
    
    @Column(name = "CODIGO_CONTRATO", nullable = false, unique = true, length = 50) 
    private String codigoContrato;
    
    @Column(name = "FECHA_INICIO", nullable = false) 
    private java.time.LocalDate fechaInicio;
    
    @Column(name = "FECHA_FIN", nullable = false) 
    private java.time.LocalDate fechaFin;
    
    @Column(name = "DESCUENTO_PORCENTAJE") 
    private BigDecimal descuentoPorcentaje;
    
    @Enumerated(EnumType.STRING) 
    private EstadoContrato estado = EstadoContrato.ACTIVO;
}