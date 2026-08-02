package com.systemicr2.dtfutbol.service;

import com.systemicr2.dtfutbol.model.Player;
import com.systemicr2.dtfutbol.model.TrainingSession;
import com.systemicr2.dtfutbol.repository.PlayerRepository;
import com.systemicr2.dtfutbol.repository.TrainingSessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TrainingSessionRepository trainingRepository;

        // Fíjate en los parámetros: String para el DNI, Long para el ID del entrenamiento
        @Transactional
        public Player addTrainingToPlayer(String dni, Long trainingId) {
        // 1. Buscamos al jugador por su DNI (String)
        Player player = playerRepository.findById(dni).orElseThrow();

        // 2. Buscamos el entrenamiento por su ID (Long)
        TrainingSession training = trainingRepository.findById(trainingId).orElseThrow();

        // 3. Añadimos el entrenamiento a la lista del jugador
        player.getTrainingSessions().add(training);

        // 4. Guardamos la mutación en la base de datos
        return playerRepository.save(player);
    }

    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public List<Player> getPlayersByTeam(Long teamId) {

        return playerRepository.findByTeamId(teamId);

    }

    public Player getPlayerById(String id) {
        return playerRepository.findById(id)
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
