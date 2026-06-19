package com.biblioteca.app.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data // Aquí sí podemos usar Data porque no hay relaciones complejas
public class InventarioVentaId implements java.io.Serializable {
    @Column(name = "SUCURSAL_ID") 
    private Integer sucursalId;
    
    @Column(name = "LIBRO_ID") 
    private Integer libroId;
}