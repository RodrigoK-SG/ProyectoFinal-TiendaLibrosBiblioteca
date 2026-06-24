package com.biblioteca.app.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/usuarios")
public class AdminUsuarioControlador {

    // @Autowired
    // private UsuarioService usuarioService;

    @GetMapping
    public String verUsuarios(Model model) {
        // Aquí cargarías la lista de usuarios desde la BD
        // model.addAttribute("usuarios", usuarioService.listarTodos());
        
        return "administrador/usuarios"; // Retorna la vista HTML en templates/administrador/usuarios.html
    }

    @PostMapping("/nuevo")
    public String registrarUsuario(@RequestParam("nombreCompleto") String nombreCompleto,
                                   @RequestParam("password") String password,
                                   @RequestParam("email") String email,
                                   @RequestParam("rol") String rol,
                                   @RequestParam(value = "activo", defaultValue = "false") boolean activo) {
        // Lógica para encriptar contraseña y guardar el nuevo usuario
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/editar")
    public String editarUsuario(@RequestParam("id") Long id,
                                @RequestParam("nombreCompleto") String nombreCompleto,
                                @RequestParam(value = "password", required = false) String password,
                                @RequestParam("email") String email,
                                @RequestParam("rol") String rol,
                                @RequestParam(value = "activo", defaultValue = "false") boolean activo) {
        // Lógica para actualizar usuario. Si 'password' está vacío, no se actualiza la clave.
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/desactivar")
    public String desactivarUsuario(@RequestParam("id") Long id) {
        // Lógica para cambiar el estado del usuario a inactivo (no borrar de la BD)
        // usuarioService.desactivar(id);
        return "redirect:/admin/usuarios";
    }
}