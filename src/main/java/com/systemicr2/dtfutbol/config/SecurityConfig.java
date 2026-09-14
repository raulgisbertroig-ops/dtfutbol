package com.systemicr2.dtfutbol.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    // Este "Bean" es una herramienta que dejamos en la caja de herramientas de Spring.
    // A partir de ahora, cualquier parte de tu código podrá pedir un "PasswordEncoder"
    // y Spring le entregará este encriptador BCrypt.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {

        // 1. Desactivamos CSRF. Como somos una API REST y no una página web tradicional con formularios HTML, no necesitamos esta protección.
        http.csrf(csrf -> csrf.disable())

                // 2. Configuramos las reglas de acceso a las rutas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/error").permitAll()      // Abrimos la ruta de auth (Solo dejamos público el login)
                        .anyRequest().authenticated()                              // REGLA GENERAL: Cualquier otra ruta requiere contraseña (Todo lo demás incluido /api/players requiere token)
                )
                .sessionManagement(session -> session.sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS));

        // 3. Insertamos tu filtro JwtRequestFilter antes del filtro por defecto de Spring
        http.addFilterBefore(jwtRequestFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }
}