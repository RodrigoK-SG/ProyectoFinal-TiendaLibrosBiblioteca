package com.biblioteca.app.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.biblioteca.app.servicio.LibroServicio;
import com.biblioteca.app.servicio.MovimientosInventarioServicio;
import com.biblioteca.app.servicio.InventarioVentaServicio;
import com.biblioteca.app.modelo.MovimientosInventario;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/almacen")
@RequiredArgsConstructor
public class AlmacenControlador {

    private final LibroServicio libroServicio;
    private final MovimientosInventarioServicio movimientosServicio;
    private final InventarioVentaServicio inventarioServicio;

    // --- VISTA DE MOVIMIENTOS ---
    @GetMapping("/movimientos")
    public String verMovimientos(Model model) {
        // Pasamos todos los libros activos para el select del modal
        model.addAttribute("libros", libroServicio.listarLibrosParaTienda());
        
        // Preparamos un objeto vacío para el formulario
        model.addAttribute("nuevoMovimiento", new MovimientosInventario());
        
        /* * Nota: Para la tabla general, idealmente necesitas un método en tu servicio 
         * como movimientosRepository.findAll() o buscar por Sucursal. 
         * Si lo tienes, agrégalo así:
         * model.addAttribute("listaMovimientos", movimientosServicio.listarTodos(1)); 
         */
        
        return "movimientos";
    }

    // --- GUARDAR MOVIMIENTO DESDE EL MODAL ---
    @PostMapping("/movimientos/registrar")
    public String registrarMovimiento(@ModelAttribute("nuevoMovimiento") MovimientosInventario movimiento) {
        // Aquí asumimos que la Sucursal Central es la ID 1
        // movimiento.setSucursalId(1); 
        
        movimientosServicio.registrarMovimiento(movimiento);
        return "redirect:/almacen/movimientos";
    }

    // --- VISTA DE STOCK ---
    @GetMapping("/stock")
    public String verStock(Model model) {
        // Pasamos la lista de libros a la vista de stock
        model.addAttribute("libros", libroServicio.listarTodosLosLibros());
        return "stock";
    }
}