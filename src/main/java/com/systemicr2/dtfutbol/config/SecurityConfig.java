package com.systemicr2.dtfutbol.config;

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

    // Este "Bean" es una herramienta que dejamos en la caja de herramientas de Spring.
    // A partir de ahora, cualquier parte de tu código podrá pedir un "PasswordEncoder"
    // y Spring le entregará este encriptador BCrypt.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Desactivamos CSRF. Como somos una API REST y no una página web tradicional con formularios HTML, no necesitamos esta protección.
                .csrf(csrf -> csrf.disable())

                // 2. Configuramos las reglas de acceso a las rutas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/players/**").permitAll() // TEMPORAL: Abrimos la puerta a todo lo que cuelgue de /players
                        .anyRequest().authenticated()                              // REGLA GENERAL: Cualquier otra ruta requiere contraseña
                );
        return http.build();
    }
}
