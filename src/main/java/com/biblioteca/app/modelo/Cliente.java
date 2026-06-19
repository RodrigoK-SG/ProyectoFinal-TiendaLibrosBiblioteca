package com.biblioteca.app.modelo;

import com.biblioteca.app.modelo.enums.TipoCliente;
import com.biblioteca.app.modelo.enums.TipoDocumento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CLIENTE")
@Getter @Setter @NoArgsConstructor
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @OneToOne(fetch = FetchType.LAZY) // Relación 1 a 1 porque USUARIO_ID es UNIQUE
    @JoinColumn(name = "USUARIO_ID") 
    private Usuario usuario;
    
    @Column(name = "NOMBRE_RAZON_SOCIAL", nullable = false, length = 150) 
    private String nombreRazonSocial;
    
    @Enumerated(EnumType.STRING) @Column(name = "TIPO_CLIENTE", nullable = false) 
    private TipoCliente tipoCliente;
    
    @Enumerated(EnumType.STRING) @Column(name = "TIPO_DOCUMENTO", nullable = false) 
    private TipoDocumento tipoDocumento;
    
    @Column(name = "NUMERO_DOCUMENTO", nullable = false, unique = true, length = 20) 
    private String numeroDocumento;
    
    @Column(name = "EMAIL_CONTACTO", length = 100) 
    private String emailContacto;
    
    @Column(length = 20) 
    private String telefono;
    
    private Boolean activo = true;
}
