package com.biblioteca.app.controlador;

import com.biblioteca.app.modelo.Rol;
import com.biblioteca.app.modelo.Usuario;
import com.biblioteca.app.repositorio.RolRepositorio;
import com.biblioteca.app.repositorio.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepositorio usuarioRepository;
    private final RolRepositorio rolRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/registro")
    public String registrarUsuarioWeb(
            @RequestParam("nombreRazonSocial") String nombre,
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        
        // 1. Verificamos que el email no exista
        if (usuarioRepository.findByEmail(email).isPresent()) {
            return "redirect:/catalogo/vista-login?errorRegistro=EmailYaExiste";
        }

        // 2. Creamos el usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreCompleto(nombre); // Tu BD pide NOMBRE_COMPLETO
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setPasswordHash(passwordEncoder.encode(password)); // ¡La contraseña se guarda encriptada!
        nuevoUsuario.setActivo(true);

     // 3. Asignamos el rol por defecto: CLIENTE_WEB
        Rol rolCliente = rolRepository.findByNombre("CLIENTE_WEB")
                .orElseThrow(() -> new RuntimeException("El rol CLIENTE_WEB no existe en la BD"));

        // Usamos List y ArrayList para que coincida con tu clase Usuario
        List<Rol> roles = new ArrayList<>();
        roles.add(rolCliente);
        nuevoUsuario.setRoles(roles);

        // 4. Guardamos en la BD
        usuarioRepository.save(nuevoUsuario);

        // 5. Redirigimos al login para que ingrese con su nueva cuenta
        return "redirect:/catalogo/vista-login?registroExitoso=true";
    }
}