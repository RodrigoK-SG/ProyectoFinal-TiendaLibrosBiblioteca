package com.biblioteca.app.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.biblioteca.app.modelo.Usuario;


public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer>{
	boolean existsByEmail(String email);
	List<Usuario> findByActivoTrue();
	Optional<Usuario> findByEmail(String email);
	@Query("SELECT u FROM Usuario u JOIN u.roles r WHERE r.nombre != 'CLIENTE_WEB'")
	List<Usuario> findUsuariosNoClientes();
}
