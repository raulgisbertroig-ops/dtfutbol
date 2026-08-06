package com.systemicr2.dtfutbol.service;

import com.systemicr2.dtfutbol.repository.TeamFinancialRepository;
import com.systemicr2.dtfutbol.repository.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class TeamFinancialService {

    private final TeamFinancialRepository financialRepository;
    private final TeamRepository teamRepository;

    public TeamFinancialService(TeamFinancialRepository financialRepository, TeamRepository teamRepository) {
        this.financialRepository = financialRepository;
        this.teamRepository = teamRepository;
    }

    public boolean canAffordNewPlayer(Long teamId, BigDecimal newPlayerSalary, BigDecimal teamBudgetLimit) {

        if (!teamRepository.existsById(teamId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El equipo con ID " + teamId + " no existe.");
        }

        BigDecimal currentPayroll = financialRepository.calculateTotalActivePayrollByTeamId(teamId);


        if (currentPayroll ==null) {
            currentPayroll = BigDecimal.ZERO;
        }

        BigDecimal projectedPayroll = currentPayroll.add(newPlayerSalary);
        return projectedPayroll.compareTo(teamBudgetLimit) <= 0;
    }
}









