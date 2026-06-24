package com.biblioteca.app.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/sucursales")
public class AdminSucursalControlador {

    // @Autowired
    // private SucursalService sucursalService;

    @GetMapping
    public String verSucursales(Model model) {
        // Aquí cargarías la lista de sucursales desde la BD
        // model.addAttribute("sucursales", sucursalService.listarTodas());
        return "administrador/sucursales"; // Ubicación: templates/administrador/sucursales.html
    }

    @PostMapping("/nuevo")
    public String registrarSucursal(@RequestParam("nombreSede") String nombreSede,
                                    @RequestParam("direccion") String direccion,
                                    @RequestParam("telefono") String telefono,
                                    @RequestParam(value = "servicioBiblioteca", defaultValue = "false") boolean servicioBiblioteca) {
        // Lógica para guardar la nueva sede
        return "redirect:/admin/sucursales";
    }

    @PostMapping("/editar")
    public String editarSucursal(@RequestParam("id") Long id,
                                 @RequestParam("nombreSede") String nombreSede,
                                 @RequestParam("direccion") String direccion,
                                 @RequestParam("telefono") String telefono,
                                 @RequestParam(value = "servicioBiblioteca", defaultValue = "false") boolean servicioBiblioteca) {
        // Lógica para actualizar los datos de la sede existente
        return "redirect:/admin/sucursales";
    }

    @PostMapping("/desactivar")
    public String desactivarSucursal(@RequestParam("id") Long id) {
        // Lógica para cambiar el estado a inactivo/desactivado (Borrado lógico seguro)
        // sucursalService.desactivar(id);
        return "redirect:/admin/sucursales";
    }
}