package com.systemicr2.dtfutbol.repository;

import com.systemicr2.dtfutbol.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Importación crítica

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {
    List<Player> findByTeamId(Long teamId);

}