package com.biblioteca.app.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    // @Autowired
    // private LibroService libroService;
    // @Autowired
    // private UsuarioService usuarioService;
    // @Autowired
    // private SucursalService sucursalService;

    @GetMapping
    public String verCatalogo(Model model) {
        // Aquí cargarías los datos reales desde la base de datos
        // model.addAttribute("libros", libroService.listarTodos());
        // model.addAttribute("totalLibrosActivos", libroService.contarActivos());
        // model.addAttribute("totalUsuarios", usuarioService.contarTodos());
        // model.addAttribute("totalSucursales", sucursalService.contarTodas());
        
        return "administrador/catalogo"; // Retorna la vista HTML (Esto se mantiene igual)
    }

    @PostMapping("/nuevo")
    public String registrarLibro(@RequestParam("isbn") String isbn,
                                 @RequestParam("titulo") String titulo,
                                 @RequestParam("formato") String formato,
                                 @RequestParam("precioVenta") Double precioVenta,
                                 @RequestParam("precioAlquiler") Double precioAlquiler,
                                 @RequestParam(value = "portada", required = false) MultipartFile portada) {
        // Lógica para guardar el nuevo libro y la imagen
        return "redirect:/admin";
    }

    @PostMapping("/editar")
    public String editarLibro(@RequestParam("id") Long id,
                              @RequestParam("isbn") String isbn,
                              @RequestParam("categoria") String categoria,
                              @RequestParam("titulo") String titulo,
                              @RequestParam("formato") String formato,
                              @RequestParam("autor") String autor,
                              @RequestParam("precioVenta") Double precioVenta,
                              @RequestParam("editorial") String editorial,
                              @RequestParam("precioAlquiler") Double precioAlquiler,
                              @RequestParam(value = "activo", defaultValue = "false") boolean activo) {
        // Lógica para actualizar los datos del libro existente
        return "redirect:/admin";
    }

    @PostMapping("/desactivar")
    public String desactivarLibro(@RequestParam("id") Long id) {
        // Lógica para cambiar el estado de activo a false
        // libroService.desactivar(id);
        return "redirect:/admin";
    }
}