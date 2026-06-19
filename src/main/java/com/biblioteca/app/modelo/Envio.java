package com.biblioteca.app.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ENVIO")
@Getter @Setter @NoArgsConstructor
public class Envio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "PEDIDO_ID", nullable = false) 
    private Pedido pedido;
    
    @Column(nullable = false, length = 100) 
    private String transportista;
    
    @Column(name = "NUMERO_TRACKING", length = 100) 
    private String numeroTracking;
    
    @Column(name = "COSTO_ENVIO") 
    private BigDecimal costoEnvio;
    
    @Column(name = "FECHA_DESPACHO") 
    private LocalDateTime fechaDespacho;
    
    @Column(name = "FECHA_ENTREGA") 
    private LocalDateTime fechaEntrega;
}