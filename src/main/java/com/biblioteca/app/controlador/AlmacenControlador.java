package com.biblioteca.app.controlador;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.biblioteca.app.servicio.LibroServicio;
import com.biblioteca.app.servicio.MovimientosInventarioServicio;
import com.biblioteca.app.servicio.InventarioVentaServicio;
import com.biblioteca.app.servicio.SucursalServicio;
import com.biblioteca.app.modelo.MovimientosInventario;
import com.biblioteca.app.modelo.Sucursal;
import com.biblioteca.app.modelo.Libro;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/almacen")
@RequiredArgsConstructor
public class AlmacenControlador {

    private final LibroServicio libroServicio;
    private final MovimientosInventarioServicio movimientosServicio;
    private final InventarioVentaServicio inventarioServicio;
    
    // Inyectamos el servicio de la Sucursal para solucionar el error 500
    private final SucursalServicio sucursalServicio; 

    @GetMapping({"", "/"})
    public String inicioAlmacen() {
        // Redirige automáticamente a la pestaña principal
        return "redirect:/almacen/movimientos";
    }
    
    // --- VISTA DE MOVIMIENTOS ---
    @GetMapping("/movimientos")
    public String verMovimientos(Model model) {
        // Para el select del modal
        model.addAttribute("libros", libroServicio.listarLibrosParaTienda());
        
        // Para el formulario vacío
        model.addAttribute("nuevoMovimiento", new MovimientosInventario());
        
        // ¡AQUÍ ESTÁ LA MAGIA PARA LLENAR LA TABLA!
        model.addAttribute("listaMovimientos", movimientosServicio.listarTodosLosMovimientos());
        
        // Retornamos apuntando a la nueva carpeta
        return "almacenero/movimientos";
    }

    // --- GUARDAR MOVIMIENTO DESDE EL MODAL ---
    @PostMapping("/movimientos/registrar")
    public String registrarMovimiento(
            @ModelAttribute("nuevoMovimiento") MovimientosInventario movimiento,
            @RequestParam(value = "nuevoIsbn", required = false) String nuevoIsbn) {
        
        // 1. Asignamos la Sucursal
        Sucursal sucursalCentral = sucursalServicio.buscarPorId(1);
        movimiento.setSucursal(sucursalCentral); 

        // Convertimos a String por seguridad (en caso de que tipoMovimiento sea un Enum)
        String tipoStr = String.valueOf(movimiento.getTipoMovimiento());

        // 2. Lógica para Entrada vs Salida
        if ("INGRESO_PROVEEDOR".equals(tipoStr)) {
            
            // Es ENTRADA: Validamos y creamos el libro borrador
            if (nuevoIsbn != null && !nuevoIsbn.trim().isEmpty()) {
                Libro libroBorrador = new Libro();
                libroBorrador.setIsbn(nuevoIsbn);
                libroBorrador.setTitulo("Libro Nuevo - Faltan Datos (" + nuevoIsbn + ")");
                libroBorrador.setPrecioVentaActual(new BigDecimal("1.00")); 
                libroBorrador.setActivo(true);
                
                // Guardamos el libro real en la BD y lo asignamos al movimiento
                Libro libroGuardado = libroServicio.guardar(libroBorrador);
                movimiento.setLibro(libroGuardado); 
            } else {
                throw new RuntimeException("Error: Debe ingresar un ISBN para registrar la entrada.");
            }
            
        } else {
            
            // Es SALIDA: Reemplazamos el libro "fantasma" del formulario por el real de la BD
            if (movimiento.getLibro() != null && movimiento.getLibro().getId() != null) {
                Libro libroExistente = libroServicio.buscarPorId(movimiento.getLibro().getId());
                movimiento.setLibro(libroExistente);
            } else {
                throw new RuntimeException("Error: Debe seleccionar un libro válido de la lista.");
            }
        }

        // 3. Guardamos el movimiento (ahora sí, con entidades reales)
        movimientosServicio.registrarMovimiento(movimiento);
        
        // El redirect se mantiene igual porque es una URL, no un archivo HTML
        return "redirect:/almacen/movimientos";
    }

    // --- VISTA DE STOCK ---
    @GetMapping("/stock")
    public String verStock(Model model) {
        model.addAttribute("libros", libroServicio.listarTodosLosLibros());
        
        // Retornamos apuntando a la nueva carpeta
        return "almacenero/stock";
    }

    // --- VISTA DE ALERTAS ---
    @GetMapping("/alertar")
    public String verAlertas(Model model) {
        // NOTA: Por ahora la vista mostrará los datos estáticos del HTML.
        // Cuando estés listo para hacerlo dinámico, puedes descomentar y usar una línea como esta:
        // model.addAttribute("librosEnAlerta", inventarioServicio.listarAlertasStock());
        
        // Retornamos apuntando a la vista "alertas.html" dentro de la carpeta "almacenero"
        return "almacenero/alertar";
    }
}