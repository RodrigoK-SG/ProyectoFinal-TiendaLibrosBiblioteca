package com.biblioteca.app.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CARRITO")
@Getter @Setter @NoArgsConstructor
public class Carrito {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "CLIENTE_ID", nullable = false, unique = true) 
    private Cliente cliente;
}
