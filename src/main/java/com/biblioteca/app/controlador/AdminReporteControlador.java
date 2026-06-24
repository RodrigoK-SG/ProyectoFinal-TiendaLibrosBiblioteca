package com.biblioteca.app.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/reportes")
public class AdminReporteControlador {

    @GetMapping
    public String verReportes(Model model) {
        // En un escenario real, estos valores vendrían de consultas a la base de datos (Ej: SUM, COUNT)
        model.addAttribute("ingresosTotales", "15,400");
        model.addAttribute("librosVendidos", "340");
        model.addAttribute("prestamosActivos", "85");
        model.addAttribute("mesActual", "Mayo 2026");
        
        return "administrador/reportes"; // Ubicación: templates/administrador/reportes.html
    }
}