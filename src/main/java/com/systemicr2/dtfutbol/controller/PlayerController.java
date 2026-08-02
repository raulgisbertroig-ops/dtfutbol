package com.systemicr2.dtfutbol.controller;

import jakarta.validation.Valid;
import com.systemicr2.dtfutbol.model.Player;
import com.systemicr2.dtfutbol.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Player> createPlayer(@Valid @RequestBody Player player) {
        Player savedPlayer = playerService.createPlayer(player);
        return new ResponseEntity<>(savedPlayer, HttpStatus.CREATED);
    }

    @PostMapping("/{dni}/trainings/{trainingId}")
    public ResponseEntity<Player> assignTraining(@PathVariable String dni, @PathVariable Long trainingId) {
        // Llamamos al servicio pasando el String (dni) y el Long (trainingId)
        Player updatedPlayer = playerService.addTrainingToPlayer(dni, trainingId);
        return new ResponseEntity<>(updatedPlayer, HttpStatus.OK);
    }

    // READ (Todos)
    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        // Asumo que ya tienes el método getAllPlayers() en tu PlayerService
        List<Player> players = playerService.getAllPlayers();
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @GetMapping("/{dni}")
    public ResponseEntity<com.systemicr2.dtfutbol.dto.PlayerResponseDTO> getPlayer(@PathVariable String dni) {
        com.systemicr2.dtfutbol.dto.PlayerResponseDTO dto = playerService.getPlayerDTO(dni);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<Player>> getPlayersByTeam(@PathVariable Long teamId) {
        return new ResponseEntity<>(playerService.getPlayersByTeam(teamId), HttpStatus.OK);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable String id, @RequestBody Player playerDetails) {
        return new ResponseEntity<>(playerService.updatePlayer(id, playerDetails), HttpStatus.OK);

    }
    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable String id) {
        playerService.deletePlayer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
    }
}