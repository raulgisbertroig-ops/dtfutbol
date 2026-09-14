package com.systemicr2.dtfutbol.config;

import com.systemicr2.dtfutbol.model.*;
import com.systemicr2.dtfutbol.repository.*;
import com.systemicr2.dtfutbol.model.enums.CategoryLevel;
import com.systemicr2.dtfutbol.model.enums.Modality;
import com.systemicr2.dtfutbol.repository.MatchEventRepository;
import com.systemicr2.dtfutbol.service.PlayerStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRuleRepository categoryRuleRepository;
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final MatchEventRepository matchEventRepository;
    private final PlayerStatsService playerStatsService;

    @Override
    public void run(String... args) {

        // Solo creamos el usuario si la base de datos está vacía
        if (userRepository.findByUsername("raul.coach").isEmpty()) {

            AppUser coach = new AppUser();
            coach.setUsername("raul.coach");
            // ¡ATENCIÓN A ESTA LÍNEA! Aquí ocurre la magia criptografica
            coach.setPassword(passwordEncoder.encode("dtfutbol2026"));
            coach.setRole("ROLE_COACH");

            userRepository.save(coach);
            System.out.println("✅ Entrenador inicial creado y encriptado con éxito.");
        }
        // Idepotencia: Solo insertamos si la tabla esta vacía
        if (categoryRuleRepository.count() == 0) {

            CategoryRule defaultRule = new CategoryRule();
            defaultRule.setName("Amistoso Standard F11");
            defaultRule.setModality(Modality.F11);
            defaultRule.setLevel(CategoryLevel.AMATEUR);
            defaultRule.setNumberOfPeriods(2); // 2 Partes
            defaultRule.setPeriodDurationMinutes(45); // 45 minutos
            defaultRule.setFlyingSubstitutions(false);

            CategoryRule youthRule = new CategoryRule();
            youthRule.setName("Amistoso Base F7");
            youthRule.setModality(Modality.F7);
            youthRule.setLevel(CategoryLevel.YOUTH);
            youthRule.setNumberOfPeriods(4); // 4 partes
            youthRule.setPeriodDurationMinutes(12); // de 12 minutos
            youthRule.setFlyingSubstitutions(true);

            categoryRuleRepository.saveAll(List.of(defaultRule, youthRule));

            System.out.println("DataInitializer: category rules inyectadas por defecto.");

            // 3. Inyectar Equipos, Partido y Jugador para pruebas de Postman
            if (matchRepository.count() == 0) {
                // Recuperamos la categoría "Amistoso Standard F11" que acabas de crear arriba
                CategoryRule rule = categoryRuleRepository.findAll().get(0);

                Team home = new Team();
                home.setName("Real Madrid CF");
                home.setCategoryRule(rule);
                teamRepository.save(home);

                Team away = new Team();
                away.setName("Rayo Vallecano FC");
                away.setCategoryRule(rule);
                teamRepository.save(away);

                Match match = new Match();
                match.setMatchDate(java.time.LocalDateTime.of(2026, 9, 15, 10, 0));
                match.setHomeTeam(home);
                match.setAwayTeam(away);
                match.setCategoryRule(rule);
                matchRepository.save(match);

                Player player = new Player();
                player.setDni("99999999Z");
                player.setName("Jugador Prueba");
                player.setPosition("DELANTERO");
                player.setStatus("ACTIVE");
                playerRepository.save(player);

                System.out.println("✅ Partido (ID 1) y Jugador (99999999Z) inyectados con éxito.");
            }
        }
    }
}

