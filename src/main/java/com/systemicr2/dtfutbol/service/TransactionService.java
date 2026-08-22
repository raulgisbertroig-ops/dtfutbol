package com.systemicr2.dtfutbol.service;

import com.systemicr2.dtfutbol.dto.TransactionRequestDTO;
import com.systemicr2.dtfutbol.dto.TransactionResponseDTO;
import com.systemicr2.dtfutbol.model.Budget;
import com.systemicr2.dtfutbol.model.Transaction;
import com.systemicr2.dtfutbol.repository.BudgetRepository;
import com.systemicr2.dtfutbol.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    // Necesitamos dos conexiones a disco duro distintas
    private final TransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;

    public TransactionService(TransactionRepository transactionRepository, BudgetRepository budgetRepository) {
        this.transactionRepository = transactionRepository;
        this.budgetRepository = budgetRepository;
    }

    public TransactionResponseDTO createTransaction(TransactionRequestDTO requestDTO) {
        // 1. Extraer el Presupuesto del disco duro. Si no existe, abortar ejecución.
        Budget linkedBudget = budgetRepository.findById(requestDTO.budgetId())
                .orElseThrow(() -> new RuntimeException("Presupuesto no encontrado en el sistema."));
        // 2. Mapeo Manuala memoria RAM
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(requestDTO.amount());
        newTransaction.setBudget(linkedBudget); // Inyectamos el objeto completo

        // 3. Persistencia
        Transaction savedTransaction = transactionRepository.save(newTransaction);

        // 4. Retorno de la red
        return new TransactionResponseDTO(
                savedTransaction.getId(),
                savedTransaction.getAmount(),
                savedTransaction.getBudget().getId()
        );
    }
}
