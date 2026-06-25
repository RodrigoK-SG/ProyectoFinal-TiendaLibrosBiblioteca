package com.biblioteca.app.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.biblioteca.app.modelo.enums.CanalVenta;
import com.biblioteca.app.modelo.enums.EstadoPedido;
import com.biblioteca.app.modelo.enums.TipoEntrega;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PEDIDO")
@Getter @Setter @NoArgsConstructor
public class Pedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "CLIENTE_ID", nullable = false) 
    private Cliente cliente;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "SUCURSAL_ID") 
    private Sucursal sucursal;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "USUARIO_VENDEDOR_ID") 
    private Usuario vendedor;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "CONTRATO_ID") 
    private Contrato contrato;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "DIRECCION_ENVIO_ID") 
    private DireccionEnvio direccionEnvio;
    
    @Enumerated(EnumType.STRING) @Column(name = "CANAL_VENTA", nullable = false) 
    private CanalVenta canalVenta;
    
    @Enumerated(EnumType.STRING) @Column(name = "TIPO_ENTREGA", nullable = false) 
    private TipoEntrega tipoEntrega;
    
    @Enumerated(EnumType.STRING) @Column(name = "ESTADO_ACTUAL") 
    private EstadoPedido estadoActual;
    
    @Column(nullable = false) 
    private BigDecimal total;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles = new ArrayList<>();
    
    @Column(name = "FECHA_PEDIDO", nullable = false)
    private LocalDateTime fechaPedido;
}
