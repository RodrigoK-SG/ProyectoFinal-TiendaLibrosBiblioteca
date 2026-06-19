package com.biblioteca.app.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DIRECCION_ENVIO")
@Getter @Setter @NoArgsConstructor
public class DireccionEnvio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "CLIENTE_ID", nullable = false) 
    private Cliente cliente;
    
    @Column(nullable = false, length = 50) 
    private String etiqueta;
    
    @Column(name = "DIRECCION_COMPLETA", nullable = false, length = 255) 
    private String direccionCompleta;
    
    @Column(nullable = false, length = 100) 
    private String ciudad;
    
    @Column(name = "CODIGO_POSTAL", length = 20) 
    private String codigoPostal;
}