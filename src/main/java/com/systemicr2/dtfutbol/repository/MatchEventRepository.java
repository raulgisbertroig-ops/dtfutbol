package com.systemicr2.dtfutbol.repository;

import com.systemicr2.dtfutbol.model.MatchEvent;
import com.systemicr2.dtfutbol.model.enums.EventType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchEventRepository extends JpaRepository<MatchEvent, Long> {

    // We will add customs queries here later If we need them!

    @Query("SELECT COUNT(m) FROM MatchEvent m WHERE m.player.id = :playerId AND m.eventType = :eventType")
    long countByPlayerIdAndEventType(@Param("playerId") Long playerId, @Param("eventType") EventType eventType);

}
