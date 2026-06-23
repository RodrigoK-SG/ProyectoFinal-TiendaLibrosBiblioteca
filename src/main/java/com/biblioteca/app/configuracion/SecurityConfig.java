package com.biblioteca.app.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) 
            
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/error").permitAll() 
                .requestMatchers("/", "/catalogo", "/catalogo/**", "/vendedor/**").permitAll()
                .requestMatchers("/css/**", "/js/**", "/img/**", "/static/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .defaultSuccessUrl("/catalogo/vista-login", true)
                .permitAll()
            )
            .logout(logout -> logout
                    // Le decimos explícitamente cuál es la ruta para cerrar sesión
                    .logoutUrl("/logout")
                    // A dónde te va a redirigir cuando salgas
                    .logoutSuccessUrl("/catalogo/vista-login")
                    .permitAll()
                );

        return http.build();
    }
}