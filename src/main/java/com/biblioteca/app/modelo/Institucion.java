package com.biblioteca.app.modelo;

import com.biblioteca.app.modelo.enums.TipoInstitucion;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "INSTITUCION")
@Getter @Setter @NoArgsConstructor
public class Institucion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "CLIENTE_ID", nullable = false, unique = true) 
    private Cliente cliente;
    
    @Enumerated(EnumType.STRING) @Column(name = "TIPO_INSTITUCION", nullable = false) 
    private TipoInstitucion tipoInstitucion;
    
    @Column(name = "PERSONA_CONTACTO", length = 150) 
    private String personaContacto;
}
