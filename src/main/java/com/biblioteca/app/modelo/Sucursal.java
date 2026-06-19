package com.biblioteca.app.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SUCURSAL")
@Getter @Setter @NoArgsConstructor
public class Sucursal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 100) 
    private String nombre;
    
    @Column(nullable = false, length = 255) 
    private String direccion;
    
    @Column(length = 20) 
    private String telefono;
    
    @Column(name = "TIENE_BIBLIOTECA") 
    private Boolean tieneBiblioteca = false;
}
