package com.systemicr2.dtfutbol.service;

import com.systemicr2.dtfutbol.model.Player;
import com.systemicr2.dtfutbol.model.Team;
import com.systemicr2.dtfutbol.repository.PlayerRepository;
import com.systemicr2.dtfutbol.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    public Player createPlayer(Player player) {
        // 1. Extraer el equipo real de la base de datos para ver su presupuesto
        Team team = teamRepository.findById(player.getTeam().getId())
                .orElseThrow(() -> new RuntimeException("Error: Equipo no encontrado"));

        // 2. Lógica de Fair Play Financiero
        if (player.getMarketValue() != null && team.getBudget() != null) {
            if (player.getMarketValue() > team.getBudget()) {
                throw new IllegalArgumentException("Fair Play Financiero: El valor del jugador supera el presupuesto del equipo.");
            }
        }

        // 3. Persistencia si pasa el filtro
        return playerRepository.save(player);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public List<Player> getPlayersByTeam(Long teamId) {

        return playerRepository.findByTeamId(teamId);

    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Jugador no encontrado en el sistema"));
    }
    public Player updatePlayer(Long id, Player playerDetails) {


        Player player = getPlayerById(id);


        player.setName(playerDetails.getName());


        player.setPosition(playerDetails.getPosition());


        return playerRepository.save(player);


    }
    public void deletePlayer(Long id) {


        playerRepository.deleteById(id);


    }
}
