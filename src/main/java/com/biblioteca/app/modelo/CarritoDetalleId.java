package com.biblioteca.app.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Embeddable
@Data // Usamos @Data de Lombok porque genera automáticamente equals() y hashCode(), obligatorios para llaves compuestas
public class CarritoDetalleId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "CARRITO_ID")
    private Integer carritoId;

    @Column(name = "LIBRO_ID")
    private Integer libroId;
}
