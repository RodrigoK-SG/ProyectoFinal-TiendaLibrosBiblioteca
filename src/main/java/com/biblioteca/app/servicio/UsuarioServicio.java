package com.biblioteca.app.servicio;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.app.modelo.Usuario;
import com.biblioteca.app.repositorio.UsuarioRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServicio {

    private final UsuarioRepositorio usuarioRepository;
    // IMPORTANTE: Aquí inyectarías tu PasswordEncoder (BCrypt)
    // private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario guardar(Usuario usuario) {
        // REGLA 1: Validar email único
        if (usuario.getId() == null && usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado.");
        }

        // REGLA 2: Encriptación de contraseña (Esto es obligatorio en producción)
        // String passwordEncriptada = passwordEncoder.encode(usuario.getPasswordHash());
        // usuario.setPasswordHash(passwordEncriptada);

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void cambiarEstado(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Toggle (si está activo, lo desactiva, y viceversa)
        usuario.setActivo(!usuario.getActivo());
        usuarioRepository.save(usuario);
    }
}
