package com.biblioteca.app.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biblioteca.app.servicio.LibroServicio;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/vendedor")
@RequiredArgsConstructor // <-- Muy importante para inyectar el servicio
public class VendedorWebController {

    private final LibroServicio libroServicio;

    // 1. Módulo de Venta Física (Punto de Caja)
    @GetMapping("/caja")
    public String verCaja(Model model) {
        // Le pasamos la lista real de libros a la plantilla
        model.addAttribute("libros", libroServicio.listarLibrosParaTienda());
        return "vendedor/caja"; 
    }

    // 2. Módulo de Cotizaciones (Listado)
    @GetMapping("/cotizaciones")
    public String verCotizaciones() {
        return "vendedor/cotizaciones"; 
    }

    // 3. Módulo de Cotizaciones (Crear Nueva)
    @GetMapping("/cotizaciones/crear")
    public String crearCotizacion() {
        return "vendedor/nueva-cotizacion"; 
    }
}