package com.systemicr2.dtfutbol.repository;

import com.systemicr2.dtfutbol.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}