package com.systemicr2.dtfutbol.config;

import com.systemicr2.dtfutbol.model.AppUser;
import com.systemicr2.dtfutbol.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Inyectamos el repositorio de usuarios y la encriptadora
    public DataInitializer(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Solo creamos el usuario si la base de ddatos está vacía
        if (userRepository.findByUsername("raul.coach").isEmpty()) {

            AppUser coach = new AppUser();
            coach.setUsername("raul.coach");

            // ¡ATENCIÓN A ESTA LÍNEA! Aquí ocurre la magia criptografica
            coach.setPassword(passwordEncoder.encode("dtfutbol2026"));

            coach.setRole("ROLE_COACH");

            userRepository.save(coach);
            System.out.println("✅ Entrenador inicial creado y encriptado con éxito.");
        }
    }
}

