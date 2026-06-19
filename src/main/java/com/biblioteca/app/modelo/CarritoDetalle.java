package com.biblioteca.app.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CARRITO_DETALLE")
@Getter @Setter @NoArgsConstructor
public class CarritoDetalle {
    @EmbeddedId 
    private CarritoDetalleId id; // <-- Llave compuesta
    
    @ManyToOne(fetch = FetchType.LAZY) @MapsId("carritoId") @JoinColumn(name = "CARRITO_ID") 
    private Carrito carrito;
    
    @ManyToOne(fetch = FetchType.LAZY) @MapsId("libroId") @JoinColumn(name = "LIBRO_ID") 
    private Libro libro;
    
    @Column(nullable = false) 
    private Integer cantidad;
}
