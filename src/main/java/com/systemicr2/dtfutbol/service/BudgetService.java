package com.systemicr2.dtfutbol.service;

import com.systemicr2.dtfutbol.dto.BudgetRequestDTO;
import com.systemicr2.dtfutbol.dto.BudgetResponseDTO;
import com.systemicr2.dtfutbol.model.Budget;
import com.systemicr2.dtfutbol.repository.BudgetRepository;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {

    // Dependencia: El cable hacia el disco duro
    private final BudgetRepository budgetRepository;

    // Inyección por constructor
    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    // Método de Escritura (Creación)
    public BudgetResponseDTO createBudget(BudgetRequestDTO requestDTO) {
        // 1. Mapeo Manual: DTO de red -> Entidad RAM
        Budget newBudget = new Budget();
        newBudget.setCategory(requestDTO.category());
        newBudget.setTotalAmount(requestDTO.totalAmount());

        // 2. Persistencia en Disco (MySQL)
        Budget savedBudget = budgetRepository.save(newBudget);

        // 3. Mapeo Manual: Entidad RAM -> DTO de salida
        return new BudgetResponseDTO(
                savedBudget.getId(),
                savedBudget.getCategory(),
                savedBudget.getTotalAmount()
        );
    }
}