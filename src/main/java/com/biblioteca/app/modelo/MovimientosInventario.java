package com.biblioteca.app.modelo;

import com.biblioteca.app.modelo.enums.TipoMovimiento;
import com.biblioteca.app.modelo.enums.TipoReferencia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MOVIMIENTOS_INVENTARIO")
@Getter
@Setter
@NoArgsConstructor
public class MovimientosInventario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)@JoinColumn(name = "USUARIO_ID")
	private Usuario usuario;
	
	@ManyToOne(fetch = FetchType.LAZY)@JoinColumn(name = "SUCURSAL_ID", nullable = false)
	private Sucursal sucursal;
	
	@ManyToOne(fetch = FetchType.LAZY)@JoinColumn(name = "LIBRO_ID", nullable = false)
	private Libro libro;

	@Enumerated(EnumType.STRING)@Column(name = "TIPO_MOVIMIENTO", nullable = false)
	private TipoMovimiento tipoMovimiento;
	
	@Enumerated(EnumType.STRING)@Column(name = "TIPO_REFERENCIA")
	private TipoReferencia tipoReferencia;

	@Column(name = "REFERENCIA_ID")
	private Integer referenciaId;
	
	@Column(nullable = false)
	private Integer cantidad;
	
	private String descripcion;
}