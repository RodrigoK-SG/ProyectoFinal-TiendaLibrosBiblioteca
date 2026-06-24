package com.biblioteca.app.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bibliotecario")
public class BibliotecarioControlador {

    // 1. Módulo de Préstamos (Vista Principal y Modales)
    @GetMapping("/prestamos")
    public String verPrestamos() {
        // Apunta a: src/main/resources/templates/bibliotecario/prestamos.html
        return "bibliotecario/prestamos";
    }

    // 2. Módulo de Gestión de Reservas
    @GetMapping("/reservas")
    public String verReservas() {
        // Apunta a: src/main/resources/templates/bibliotecario/reservas.html
        return "bibliotecario/reservas";
    }

    // 3. Módulo de Registro de Penalizaciones
    @GetMapping("/penalizaciones")
    public String verPenalizaciones() {
        // Apunta a: src/main/resources/templates/bibliotecario/penalizaciones.html
        return "bibliotecario/penalizaciones";
    }
}