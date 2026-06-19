package com.biblioteca.app.modelo;

import java.math.BigDecimal;
import java.util.List;

import com.biblioteca.app.modelo.enums.FormatoLibro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "LIBRO")
@Getter @Setter @NoArgsConstructor
@ToString(exclude = {"autores", "categorias"})
public class Libro {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, unique = true, length = 20) 
    private String isbn;
    
    @Column(nullable = false, length = 255) 
    private String titulo;
    
    @Column(unique = true) 
    private String slug;
    
    @Column(name = "IMAGEN_PORTADA") 
    private String imagenPortada;
    
    private String sinopsis;
    
    @Column(nullable = false) 
    private Integer paginas;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false) 
    private FormatoLibro formato;
    
    @Column(name = "PRECIO_VENTA_ACTUAL", nullable = false) 
    private BigDecimal precioVentaActual;
    
    @Column(name = "PRECIO_ALQUILER_ACTUAL") 
    private BigDecimal precioAlquilerActual;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EDITORIAL_ID", nullable = false) 
    private Editorial editorial;
    
    private Boolean activo = true;

    @ManyToMany
    @JoinTable(name = "LIBRO_AUTOR", joinColumns = @JoinColumn(name = "LIBRO_ID"), inverseJoinColumns = @JoinColumn(name = "AUTOR_ID"))
    private List<Autor> autores;

    @ManyToMany
    @JoinTable(name = "LIBRO_CATEGORIA", joinColumns = @JoinColumn(name = "LIBRO_ID"), inverseJoinColumns = @JoinColumn(name = "CATEGORIA_ID"))
    private List<Categoria> categorias;
}
