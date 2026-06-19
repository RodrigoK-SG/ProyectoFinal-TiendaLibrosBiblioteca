package com.biblioteca.app.modelo;

import java.math.BigDecimal;

import com.biblioteca.app.modelo.enums.EstadoPago;
import com.biblioteca.app.modelo.enums.MetodoPago;
import com.biblioteca.app.modelo.enums.TipoTransaccion;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PAGO")
@Getter @Setter @NoArgsConstructor
public class Pago {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "PEDIDO_ID", nullable = false) 
    private Pedido pedido;
    
    @Enumerated(EnumType.STRING) @Column(name = "TIPO_TRANSACCION") 
    private TipoTransaccion tipoTransaccion;
    
    @Enumerated(EnumType.STRING) @Column(name = "METODO_PAGO", nullable = false) 
    private MetodoPago metodoPago;
    
    @Enumerated(EnumType.STRING) @Column(name = "ESTADO_PAGO") 
    private EstadoPago estadoPago;
    
    @Column(nullable = false) 
    private BigDecimal monto;
    
    @Column(name = "REFERENCIA_PAGO", length = 100) 
    private String referenciaPago;
}
