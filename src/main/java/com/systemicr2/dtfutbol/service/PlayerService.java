package com.systemicr2.dtfutbol.service;

import com.systemicr2.dtfutbol.model.Player;
import com.systemicr2.dtfutbol.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    // 1. CREATE (Crear)
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    // 2. READ (Leer todos)
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    // 3. READ (Leer uno específico por su ID)
    public Player getPlayerById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Jugador no encontrado en el sistema"));
    }

    // 4. UPDATE (Actualizar)
    public Player updatePlayer(Long id, Player playerDetails) {
        Player playerToUpdate = getPlayerById(id); // Primero verificamos que exista

        // Sobrescribimos los datos en memoria
        playerToUpdate.setName(playerDetails.getName());
        playerToUpdate.setPosition(playerDetails.getPosition());
        playerToUpdate.setShirtNumber(playerDetails.getShirtNumber());
        playerToUpdate.setDni(playerDetails.getDni());

        // Guardamos los cambios en disco
        return playerRepository.save(playerToUpdate);
    }

    // 5. DELETE (Borrar)
    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }
}