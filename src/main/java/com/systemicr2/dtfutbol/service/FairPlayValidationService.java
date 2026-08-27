package com.systemicr2.dtfutbol.service;

import com.systemicr2.dtfutbol.exception.SalaryCapExceededException;
import com.systemicr2.dtfutbol.model.Team;
import com.systemicr2.dtfutbol.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class FairPlayValidationService {

    private final TeamRepository teamRepository;

    public void validateTransfer(Long teamId, BigDecimal newPlayerSalary) {
        // 1. Recuperamos el equipo (asume que si no existe, lanza otra excepción)
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado en la DB"));

        // 2. Extraemos los valores financieros (Patrón Zer trust para evitar NPE)
        BigDecimal currentWageBill = team.getCurrentWageBill() != null ? team.getCurrentWageBill() : BigDecimal.ZERO;
        BigDecimal totalBudget = team.getBudget() != null ? team.getBudget() : BigDecimal.ZERO;

        // 3. Calculamos la proyección
        BigDecimal projectedWageBill = currentWageBill.add(newPlayerSalary);

        // 4. Auditoría FFP
        if (projectedWageBill.compareTo(totalBudget) > 0) {
            BigDecimal excess = projectedWageBill.subtract(totalBudget);
            throw new SalaryCapExceededException(
                    "Operación denegada por Fair Play Financiero. El traspaso excede el límite salarial en: " + excess
            );
        }
    }
}