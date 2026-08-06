package com.systemicr2.dtfutbol.repository;

import com.systemicr2.dtfutbol.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TeamFinancialRepository extends JpaRepository<Team, Long> {

    @Query("SELECT SUM(p.salary) FROM Player p WHERE p.team.id = :teamId AND p.status = 'ACTIVE'")
    BigDecimal calculateTotalActivePayrollByTeamId(@Param("teamId") Long teamId);
}














