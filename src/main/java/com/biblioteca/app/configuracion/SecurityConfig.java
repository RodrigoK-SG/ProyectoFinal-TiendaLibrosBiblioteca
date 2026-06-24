package com.biblioteca.app.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    // Fundamental para encriptar/desencriptar las contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactivado por ahora para facilitar el desarrollo
            
            .authorizeHttpRequests(auth -> auth
                // Rutas PÚBLICAS (Todos pueden entrar)
                .requestMatchers("/error", "/", "/catalogo", "/catalogo/detalle/**", "/catalogo/vista-login", "/registro").permitAll()
                .requestMatchers("/css/**", "/js/**", "/img/**", "/static/**").permitAll()
                
                // Rutas PROTEGIDAS POR ROL (Usamos hasAuthority porque no tienen el prefijo ROLE_)
                .requestMatchers("/admin/**").hasAuthority("ADMINISTRADOR")
                .requestMatchers("/vendedor/**").hasAuthority("VENDEDOR")
                .requestMatchers("/bibliotecario/**").hasAuthority("BIBLIOTECARIO")
                .requestMatchers("/almacen/**").hasAuthority("ALMACENERO")
                .requestMatchers("/catalogo/perfil/**", "/catalogo/checkout/**").hasAnyAuthority("CLIENTE_WEB", "ADMINISTRADOR")
                
                // Cualquier otra ruta requiere estar logueado
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/catalogo/vista-login") // La URL donde está tu HTML de login
                .loginProcessingUrl("/login") // La ruta que procesa el <form th:action="@{/login}"> (Spring lo hace automático)
                .successHandler(loginSuccessHandler) // ¡Usamos nuestro enrutador!
                .failureUrl("/catalogo/vista-login?error=true") // Si falla, recarga con error
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/catalogo/vista-login") // Al salir, volvemos al login
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }
}