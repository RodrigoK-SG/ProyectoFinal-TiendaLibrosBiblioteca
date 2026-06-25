package com.biblioteca.app.modelo;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockLibroDTO {
    private String titulo;
    private String isbn;
    private String slug;
    private String editorial;
    private BigDecimal precio;
    private Integer cantidadDisponible;
    private String estadoStock; // "ALTO", "SUFICIENTE", "BAJO", "AGOTADO"
    private boolean activoCatalogo;
}