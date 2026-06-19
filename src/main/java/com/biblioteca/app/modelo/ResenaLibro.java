package com.biblioteca.app.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "RESENA_LIBRO")
@Getter @Setter @NoArgsConstructor
public class ResenaLibro {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "LIBRO_ID", nullable = false) 
    private Libro libro;
    
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "CLIENTE_ID", nullable = false) 
    private Cliente cliente;
    
    @Column(nullable = false) 
    private Integer calificacion;
    
    private String comentario;
    
    @Column(name = "APROBADO_MODERADOR") 
    private Boolean aprobadoModerador = false;
}
