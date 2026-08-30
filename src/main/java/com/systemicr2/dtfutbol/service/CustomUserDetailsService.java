package com.systemicr2.dtfutbol.service;

import com.systemicr2.dtfutbol.model.AppUser;
import com.systemicr2.dtfutbol.repository.AppUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository userRepository;

    // Inyectamos nuestro intercomunicador (Repository) en el servicio
    public CustomUserDetailsService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Buscamos al usuario en la base de datos
        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Entrenador no encontrado: " + username));

        // 2. Lo traducimos al idioma que entiende Spring Security
        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(appUser.getRole().replace("ROLE_", "")) // Spring exige limpiar el prefijo en el builder
                .build();
    }
}

