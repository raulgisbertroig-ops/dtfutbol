package com.systemicr2.dtfutbol.controller;

import com.systemicr2.dtfutbol.dto.MatchResponseDto;
import com.systemicr2.dtfutbol.dto.MatchScoreDto;
import com.systemicr2.dtfutbol.model.Match;
import com.systemicr2.dtfutbol.service.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    // Endpoint: PUT http://localhost:8080/api/matches/1/score
    @PutMapping("/{id}/score")
    public ResponseEntity<MatchResponseDto> updateScore(
            @PathVariable Long id,
            @Valid
            @RequestBody MatchScoreDto scoreDto) {

        // 1. El servicio hace el trabajo en base de datos
        Match updatedMatch = matchService.updateMatchScore(id, scoreDto);

        // 2. Mapeamos la entidad al DTO de respuesta (Jackson serializará esto sin problemas)
        MatchResponseDto response = new MatchResponseDto();
        response.setId(updatedMatch.getId());
        response.setHomeGoals(updatedMatch.getHomeGoals());
        response.setAwayGoals(updatedMatch.getAwayGoals());

        // 3. Devolvemos el DTO limpio
        return ResponseEntity.ok(response);
    }
}