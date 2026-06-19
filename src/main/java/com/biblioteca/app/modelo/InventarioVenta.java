package com.biblioteca.app.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "INVENTARIO_VENTA")
@Getter @Setter @NoArgsConstructor
public class InventarioVenta {
    @EmbeddedId
    private InventarioVentaId id;

    // Relaciones (Solo lectura para no chocar con el EmbeddedId)
    @ManyToOne(fetch = FetchType.LAZY) @MapsId("sucursalId") @JoinColumn(name = "SUCURSAL_ID") 
    private Sucursal sucursal;
    
    @ManyToOne(fetch = FetchType.LAZY) @MapsId("libroId") @JoinColumn(name = "LIBRO_ID") 
    private Libro libro;

    @Column(name = "CANTIDAD_DISPONIBLE", nullable = false) 
    private Integer cantidadDisponible;
    
    @Column(name = "CANTIDAD_RESERVADA", nullable = false) 
    private Integer cantidadReservada;
    
    @Column(name = "STOCK_MINIMO") 
    private Integer stockMinimo;
    
    @Column(name = "STOCK_MAXIMO") 
    private Integer stockMaximo;
}
