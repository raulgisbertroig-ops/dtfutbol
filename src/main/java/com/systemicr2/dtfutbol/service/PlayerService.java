package com.systemicr2.dtfutbol.service;

import com.systemicr2.dtfutbol.model.Player;
import com.systemicr2.dtfutbol.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Player createPlayer(Player player) {
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
