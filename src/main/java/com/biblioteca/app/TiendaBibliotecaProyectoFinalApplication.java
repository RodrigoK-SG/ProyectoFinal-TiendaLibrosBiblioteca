package com.biblioteca.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = { 
	    SecurityAutoConfiguration.class, 
	    UserDetailsServiceAutoConfiguration.class 
	})
public class TiendaBibliotecaProyectoFinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiendaBibliotecaProyectoFinalApplication.class, args);
	}

}
