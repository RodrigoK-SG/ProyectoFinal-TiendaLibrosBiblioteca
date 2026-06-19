package com.biblioteca.app.modelo;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DETALLE_PEDIDO")
@Getter @Setter @NoArgsConstructor
@ToString(exclude = "pedido")
public class DetallePedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "PEDIDO_ID", nullable = false) 
    private Pedido pedido;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "LIBRO_ID", nullable = false) 
    private Libro libro;
    
    @Column(nullable = false) 
    private Integer cantidad;
    
    @Column(name = "PRECIO_UNITARIO", nullable = false) 
    private BigDecimal precioUnitario;
    
    @Column(nullable = false) 
    private BigDecimal subtotal;
}