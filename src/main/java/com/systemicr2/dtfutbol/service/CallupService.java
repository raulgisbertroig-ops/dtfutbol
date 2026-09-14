package com.systemicr2.dtfutbol.service;

import com.systemicr2.dtfutbol.model.Callup;
import com.systemicr2.dtfutbol.model.Match;
import com.systemicr2.dtfutbol.model.Player;
import com.systemicr2.dtfutbol.repository.CallupRepository;
import com.systemicr2.dtfutbol.repository.MatchRepository;
import com.systemicr2.dtfutbol.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CallupService {

    private final CallupRepository callupRepository;
    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;

    @Transactional
    public Callup addPlayerToMatch(Long matchId, String playerId, boolean isStarter, int minutesPlayed) {


        // REQUERIMIENTO 1: Busca el partido (Match) usando matchRepository.
        // Si no existe, lanza un RuntimeException("Partido no encontrado")
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Partido no encontrado con ID:" + matchId));

        // REQUERIMIENTO 2: Busca el jugador (Player) usando playerRepository.
        // Si no existe, lanza un RuntimeException("Jugador no encontrado")
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado con ID:" + playerId));

        // REQUERIMIENTO 3: Instancia en una nueva Callup (new Callup())
        // y asignale el partido, el jugador, si es titular y los minutos jugados.
        Callup callup = new Callup();
        callup.setMatch(match);
        callup.setPlayer(player);
        callup.setStarter(isStarter);
        callup.setMinutesPlayed(minutesPlayed);

        // REQUERIMIENTO 4: Guarda la convocatoria en la base de datos usando callupRepository
        // y retorna el objeto guardado.
       return callupRepository.save(callup);
    }
}

