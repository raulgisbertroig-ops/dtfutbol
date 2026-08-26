package com.systemicr2.dtfutbol.service;

import com.systemicr2.dtfutbol.dto.TransactionRequestDTO;
import com.systemicr2.dtfutbol.dto.TransactionResponseDTO;
import com.systemicr2.dtfutbol.model.Budget;
import com.systemicr2.dtfutbol.model.Transaction;
import com.systemicr2.dtfutbol.repository.BudgetRepository;
import com.systemicr2.dtfutbol.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    // Necesitamos dos conexiones a disco duro distintas
    private final TransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;

    public TransactionService(TransactionRepository transactionRepository, BudgetRepository budgetRepository) {
        this.transactionRepository = transactionRepository;
        this.budgetRepository = budgetRepository;
    }

    @Transactional
    public TransactionResponseDTO createTransaction(TransactionRequestDTO requestDTO) {
        // 1. Extraer el Presupuesto del disco duro. Si no existe, abortar ejecución.
        Budget linkedBudget = budgetRepository.findById(requestDTO.budgetId())
                .orElseThrow(() -> new RuntimeException("Presupuesto no encontrado en el sistema."));

        // 2. Cálculo Unificado (aplica a ingresos positivos y gastos negativos)
        java.math.BigDecimal projectedBalance = linkedBudget.getTotalAmount().add(requestDTO.amount());

        // 3. Validación de Lógica de Negocio (El muro de contención solo para gastos)
        if (requestDTO.amount().compareTo(java.math.BigDecimal.ZERO) < 0) {
            if (projectedBalance.compareTo(java.math.BigDecimal.ZERO) < 0) {
                throw new com.systemicr2.dtfutbol.exception.InsufficientFundsException(
                        "Operación denegada. El fichaje dejaría el presupuesto en descubierto (" + projectedBalance + "€)."
                );
            }
        }

        // 4. Mutación de Estado Universal (Actualiza RAM y DiscoDuro siempre)
        linkedBudget.setTotalAmount(projectedBalance);
        budgetRepository.save(linkedBudget);

        // 5. Mapeo Manual a memoria RAM
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(requestDTO.amount());
        newTransaction.setType(requestDTO.type());
        newTransaction.setDescription(requestDTO.description());
        newTransaction.setBudget(linkedBudget);

        // 6. Persistencia
        Transaction savedTransaction = transactionRepository.save(newTransaction);

        // 7. Retorno de la red
        return new TransactionResponseDTO(
                savedTransaction.getId(),
                savedTransaction.getAmount(),
                savedTransaction.getBudget().getId()
        );
    }
}





