package com.biblioteca.app.modelo;

import com.biblioteca.app.modelo.enums.EstadoPedido;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "HISTORIAL_ESTADO_PEDIDO")
@Getter @Setter @NoArgsConstructor
public class HistorialEstadoPedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "PEDIDO_ID", nullable = false) 
    private Pedido pedido;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "USUARIO_ID") 
    private Usuario usuario;
    
    @Enumerated(EnumType.STRING) @Column(nullable = false) 
    private EstadoPedido estado;
    
    @Column(length = 255)
    private String comentario;
    
    private String notas;
}
