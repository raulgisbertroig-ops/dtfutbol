package com.systemicr2.dtfutbol.service;

import com.systemicr2.dtfutbol.model.Player;
import com.systemicr2.dtfutbol.model.Team;
import com.systemicr2.dtfutbol.repository.TeamRepository;
import com.systemicr2.dtfutbol.model.TrainingSession;
import com.systemicr2.dtfutbol.repository.PlayerRepository;
import com.systemicr2.dtfutbol.repository.TrainingSessionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    // 1. Punteros Inmutables (Reemplazando los antiguos @Autowired)
    private final PlayerRepository playerRepository;
    private final TrainingSessionRepository trainingSessionRepository;
    private final TeamRepository teamRepository;
    private final SecurityValidationService securityValidationService;


    // --- METODOS DE NEGOCIO ---

    @Transactional
    public Player addTrainingToPlayer(String dni, Long trainingId) {
        // 1. Buscamos al jugador por su DNI
        Player player = playerRepository.findById(dni).orElseThrow();

        // 2. Buscamos el entrenamiento por su ID
        TrainingSession trainingSession = trainingSessionRepository.findById(trainingId).orElseThrow();

        // 3. Añadimos el entrenamiento a la lista del jugador
        player.getTrainingSessions().add(trainingSession);

        // 4. Guad¡rdamos la mutacion en la base de datos
        return playerRepository.save(player);
    }

    // NUEVO: Motor de creación con barrera financiera y de segfuridad (Patrón Fail-Fast)
    @Transactional
    public Player createPlayer(Player player, Long teamId) {
        // 0. BARRERA DE SEGURIDAD t-60 (Aislamiento de Datos)
        // Si raul.coach intenta meter un jugador en un teamId que no es el suyo, lanza 403 y muere aquí.
        securityValidationService.validateTeamOwnership(teamId);

        // 1. FIREWALL FFP (Capa de Negocio)
        // Llamamos al nuevo validador. Si falla, escupe la excepción...

        // 2. I/O DE BASE DE DATOS (Capa de Persistencia)
        Team officialTeam = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("El equipo con ID " + teamId + " no existe"));

        // 3. ESCRITURA
        player.setTeam(officialTeam);
        return playerRepository.save(player);
    }


    // --- A partir de aquí deben seguir tus otros métodos (getAllPlayers, etc.) ---

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public List<Player> getPlayersByTeam(Long teamId) {
        return playerRepository.findByTeamId(teamId);
    }

    public Player getPlayerById(String Id) {
        return playerRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Error: Jugador no encontrado en el sistema"));
    }

    @Transactional
    public com.systemicr2.dtfutbol.dto.PlayerResponseDTO getPlayerDTO(String dni) {
        Player player = getPlayerById(dni);
        com.systemicr2.dtfutbol.dto.PlayerResponseDTO dto = new com.systemicr2.dtfutbol.dto.PlayerResponseDTO();

        dto.dni = player.getDni();
        dto.name = player.getName();
        dto.position = player.getPosition();
        dto.teamName = player.getTeam() != null ? player.getTeam().getName() : "Sin Equipo";

        dto.trainingObjectives = player.getTrainingSessions().stream()
                .map(TrainingSession::getObjective)
                .toList();

        dto.matchesPlayed = player.getMatchesPlayed();
        dto.goalsScored = player.getGoalsScored();
        dto.assists = player.getAssists();

        // Aplicamos Zero Trust para proteger los registros antiguos (Legacy Data)
        dto.matchesPlayed = player.getMatchesPlayed() != null ? player.getMatchesPlayed() : 0;
        dto.goalsScored = player.getGoalsScored() != null ? player.getGoalsScored() : 0;
        dto.assists = player.getAssists() != null ? player.getAssists() : 0;

        return dto;
    }


    public Player updatePlayer(String id, Player playerDetails) {

        // 1. I/O: Cargamos el estado inmutable desde el disco duro a la RAM.
        Player existingPlayer = getPlayerById(id);

        // 2. Mutamos las variables permitidas en la memoria local.
        existingPlayer.setName(playerDetails.getName());
        existingPlayer.setPosition(playerDetails.getPosition());
        // existingPlayer.setMonthlySalary(playerDetails.getMonthlySalary());
        // Descomentar si aún quieres guardar el salario como dato informativo.

        // 3.I/O: Sobreescribimos el disco.
        return playerRepository.save(existingPlayer);
    }

    // --- DESTRUCCIÓN DE DATOS ---
    public void deletePlayer(String id) {

        // 1. Verificación de existencia: Reutilizamos tu método interno.
        // Si no existe, este método ya lanza un RuntimeException controlado.
        Player playerToDelete = getPlayerById(id);

        // 2. (Punto de anclaje para el futuro): Aquí desvincularemos entrenamientos
        // o devolveremos su salario al presupuesto del equipo antes de borrarlo.

        // 3. I/O: Destrucción segura del objeto en la base de datos.
        playerRepository.delete(playerToDelete);
    }
}


