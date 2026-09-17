package com.systemicr2.dtfutbol.service;

import com.systemicr2.dtfutbol.dto.MatchScoreDto;
import com.systemicr2.dtfutbol.model.Match;
import com.systemicr2.dtfutbol.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    // NIVEL 4 - CÓMO SE IMPLEMENTA: Lógica de actualización de un resultado.
    public Match updateMatchScore(Long matchId, MatchScoreDto scoreDto) {
        // 1. Buscamos el partido. Si no existe en la BD, frenamos la ejecución lanzando un error.
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Partido no encontrado con ID: " + matchId));

        // 2. Extraemos los goles del DTO que nos envía el cliente (Postman) y los asignamos a la entidad
        match.setHomeGoals(scoreDto.getHomeGoals());
        match.setAwayGoals(scoreDto.getAwayGoals());

        // 3. Al hacer save(), Hibernate detecta los cambios y lanza el UPDATE automático (magia de JPA)
        return matchRepository.save(match);
    }
}
