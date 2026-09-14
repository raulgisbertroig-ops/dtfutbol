package com.systemicr2.dtfutbol.service;

import com.systemicr2.dtfutbol.model.enums.EventType;
import com.systemicr2.dtfutbol.repository.MatchEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerStatsService {

    private final MatchEventRepository matchEventRepository;

    public long getPlayerTotalGoals(Long playerId) {
        return matchEventRepository.countByPlayerIdAndEventType(playerId, EventType.GOAL);
    }

    public long getPlayerTotalYellowCards(Long playerId) {
        return matchEventRepository.countByPlayerIdAndEventType(playerId, EventType.YELLOW_CARD);
    }
}
