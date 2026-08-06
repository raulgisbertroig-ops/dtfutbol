package com.systemicr2.dtfutbol.service;

import com.systemicr2.dtfutbol.model.Player;
import com.systemicr2.dtfutbol.model.Team;
import com.systemicr2.dtfutbol.repository.TeamRepository;
import com.systemicr2.dtfutbol.model.TrainingSession;
import com.systemicr2.dtfutbol.repository.PlayerRepository;
import com.systemicr2.dtfutbol.repository.TrainingSessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PlayerService {

    // 1. Punteros Inmutables (Reemplazando los antiguos @Autowired)
    private final PlayerRepository playerRepository;
    private final TrainingSessionRepository trainingSessionRepository;
    private final TeamFinancialService teamFinancialService;
    private final TeamRepository teamRepository;


    // 2. Inyección de dependencias por Constructor (El estandar de la industria)
    public PlayerService(PlayerRepository playerRepository,
                         TrainingSessionRepository trainingRepository, // Parámetro de entrada
                         TeamFinancialService teamFinancialService,
                         TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.trainingSessionRepository = trainingRepository; // Asignación corregida
        this.teamFinancialService = teamFinancialService;
        this.teamRepository = teamRepository;
    }

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

    // NUEVO: Motor de creación con barrera financiera (Patrón Fail-Fast)
    public Player createPlayer(Player player, Long teamId) {
        Team officialTeam = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("El equipo con ID " + teamId + " no existe"));

        BigDecimal officialBudget = BigDecimal.valueOf(officialTeam.getBudget());

        // AQUI ESTABA EL ERROR BOOLEANO. Llamamos al servicio financiero.
        boolean canAfford = teamFinancialService.canAffordNewPlayer(teamId, player.getSalary(), officialBudget);

        if (!canAfford) {
            throw new IllegalArgumentException("Presupuesto insuficiente para este fichaje. Operación denegada.");
        }

        player.setTeam(officialTeam);
        return playerRepository.save(player);
    } // <-- ÚNICA LLAVE DE CIERRE. Borra la llave extra que tenías debajo de esta.

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

        return dto;
    }

    public Player updatePlayer(String id, Player playerDetails) {
        Player player = getPlayerById(id);
        player.setName(playerDetails.getName());
        player.setPosition(playerDetails.getPosition());
        return playerRepository.save(player);
    }

    public void deletePlayer(String id) {
        playerRepository.deleteById(id);
    }
}


