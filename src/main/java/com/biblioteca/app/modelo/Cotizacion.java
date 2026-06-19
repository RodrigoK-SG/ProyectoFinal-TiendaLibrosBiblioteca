package com.biblioteca.app.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.biblioteca.app.modelo.enums.EstadoCotizacion;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "COTIZACION")
@Getter @Setter @NoArgsConstructor
public class Cotizacion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "INSTITUCION_ID", nullable = false) 
    private Institucion institucion;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "USUARIO_VENDEDOR_ID", nullable = false) 
    private Usuario vendedor;
    
    @Enumerated(EnumType.STRING) 
    private EstadoCotizacion estado = EstadoCotizacion.BORRADOR;
    
    @Column(name = "TOTAL_ESTIMADO", nullable = false) 
    private BigDecimal totalEstimado;
    
    @Column(name = "FECHA_VALIDEZ", nullable = false) 
    private LocalDateTime fechaValidez;
}
