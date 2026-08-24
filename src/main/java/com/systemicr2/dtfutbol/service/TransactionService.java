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

        // 1.5. Validación de Lógica de Negocio (Protección contra descubiertos)
        // Solo verificamos si la transacción es un gasto (amount es negativo)
        if (requestDTO.amount().compareTo(java.math.BigDecimal.ZERO) < 0) {

            // Calculamos el balance hipotético: Presupuesto Actual + Gasto (que es negativo)
            java.math.BigDecimal projectedBalance = linkedBudget.getTotalAmount().add(requestDTO.amount());

            // Si el balance proyectado es menor que cero, abortamos la ejecución lanzado la excepción
            if (projectedBalance.compareTo(java.math.BigDecimal.ZERO) < 0) {
                throw new com.systemicr2.dtfutbol.exception.InsufficientFundsException(
                        "Operación denegada. El fichaje dejaría el presupuesto en descubierto (" + projectedBalance + "€)."
                );
            }

            // 1.8. Mutación de Estado (Actualizar el Presupuesto)
            // Sobrescribimos el objeto en RAM y lo persistimos en el disco duro (MySQL)
            linkedBudget.setTotalAmount(projectedBalance);
            budgetRepository.save(linkedBudget);

        }

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
