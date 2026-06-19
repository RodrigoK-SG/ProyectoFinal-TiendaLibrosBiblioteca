package com.biblioteca.app.modelo;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data // Aquí sí podemos usar Data porque no hay relaciones complejas
public class InventarioVentaId implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "SUCURSAL_ID") 
    private Integer sucursalId;
    
    @Column(name = "LIBRO_ID") 
    private Integer libroId;
}