package com.biblioteca.app.configuracion;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        
        String redirectUrl = "/catalogo"; // Ruta por defecto (Cliente Web)
        
        // Obtenemos los roles del usuario que acaba de iniciar sesión
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        for (GrantedAuthority grantedAuthority : authorities) {
            String roleName = grantedAuthority.getAuthority();
            
            // Redirección basada en el rol de tu BD
            if (roleName.equals("ADMINISTRADOR")) {
                redirectUrl = "/admin";
                break;
            } else if (roleName.equals("VENDEDOR")) {
                redirectUrl = "/vendedor";
                break;
            } else if (roleName.equals("BIBLIOTECARIO")) {
                redirectUrl = "/bibliotecario";
                break;
            } else if (roleName.equals("ALMACENERO")) {
                redirectUrl = "/almacen";
                break;
            } else if (roleName.equals("CLIENTE_WEB")) {
                redirectUrl = "/catalogo/perfil"; // O donde quieras llevar al cliente al entrar
                break;
            }
        }
        
        response.sendRedirect(redirectUrl);
    }
}