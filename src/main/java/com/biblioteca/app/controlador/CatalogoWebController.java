package com.biblioteca.app.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;

import com.biblioteca.app.servicio.LibroServicio;
import com.biblioteca.app.servicio.CategoriaServicio;

@Controller
@RequestMapping("/catalogo")
@RequiredArgsConstructor // Inyecta automáticamente los servicios usando Lombok
public class CatalogoWebController {

    private final LibroServicio libroServicio;
    private final CategoriaServicio categoriaServicio;

    @GetMapping
    public String verCatalogoPublico(Model model) {
        // Traemos la data real procesada por los servicios de Rodrigo
        model.addAttribute("categorias", categoriaServicio.listarTodas());
        model.addAttribute("libros", libroServicio.listarLibrosParaTienda());
        
        return "index"; // Carga tu archivo index.html dentro de templates/
    }
    
    @GetMapping("/vista-login")
    public String verLogin() {
        return "login"; // Sigue cargando tu archivo login.html
    }

    @GetMapping("/registro")
    public String verRegistro() {
        return "registro"; // Va a buscar src/main/resources/templates/registro.html
    }
    
    @GetMapping("/detalle/{id}")
    public String verDetalleLibro(@PathVariable("id") Integer id, Model model) {
        // Usa el método del servicio de Rodrigo para jalar la data de MySQL por su ID
        model.addAttribute("libro", libroServicio.buscarPorId(id));
        return "detalle"; // Abre el HTML que acabamos de crear
    }
    
    @GetMapping("/checkout") // La URL en el navegador puede seguir siendo /checkout si deseas
    public String verFinalizarCompra(Model model) {
        return "pagos"; // <-- AQUÍ: Cambia "checkout" por "pagos" para que busque pagos.html
    }
}