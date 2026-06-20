package com.biblioteca.app.modelo;

import java.math.BigDecimal;
import com.biblioteca.app.modelo.enums.TipoComprobante;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "COMPROBANTE_FISCAL")
@Getter @Setter @NoArgsConstructor
public class ComprobanteFiscal {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "PEDIDO_ID", nullable = false) 
    private Pedido pedido;
    
    // Relación con el Pago que agregamos para el servicio
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAGO_ID", nullable = false)
    private Pago pago;
    
    @Enumerated(EnumType.STRING) 
    @Column(name = "TIPO_COMPROBANTE", nullable = false) 
    private TipoComprobante tipoComprobante;
    
    @Column(nullable = false, length = 10) 
    private String serie; // <-- Tu campo original (Correcto)
    
    @Column(nullable = false, length = 20) 
    private String numero; // <-- Tu campo original (Correcto)
    
    @Column(name = "MONTO_IMPUESTO", nullable = false) 
    private BigDecimal montoImpuesto;
    
    @Column(name = "MONTO_TOTAL", nullable = false) 
    private BigDecimal montoTotal;
}