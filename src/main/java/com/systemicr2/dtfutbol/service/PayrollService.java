package com.systemicr2.dtfutbol.service;

import com.systemicr2.dtfutbol.dto.TransactionRequestDTO;
import com.systemicr2.dtfutbol.model.Player;
import com.systemicr2.dtfutbol.repository.PlayerRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PayrollService {

    private final PlayerRepository playerRepository;
    private final TransactionService transactionService;

    // Inyección de Dependencias: Traemos los repositorios y servicios que ya construiste
    public PayrollService(PlayerRepository playerRepository, TransactionService transactionService) {
        this.playerRepository = playerRepository;
        this.transactionService = transactionService;
    }

    // El Cron "0 * * * * ?" significa: "Ejecutar en el segundo 0 de CADA minuto" (Solo para testing)
    // En producción usaríamos "0 0 0 1 * ?" (Día 1 de cada mes a medianoche)
    @Scheduled(cron = "0 0 0 1 * ?")
    public void executeMonthlyPayroll() {
        System.out.println("🕒 [SYSTEM] Iniciando cálculo de nóminas automático...");

        // 1. Extraer a todos los jugadores de la base de datos a la RAM
        List<Player> squad = playerRepository.findAll();

        // 2. Sumar todos los salarios usando un bucle
        BigDecimal totalPayroll = BigDecimal.ZERO;
        for (Player player : squad) {
            if (player.getMonthlySalary() != null) {
                totalPayroll = totalPayroll.add(player.getMonthlySalary());
            }
        }

        // Si la plantilla cuesta 0€, abortamos para no crear transacciones inútiles
        if (totalPayroll.compareTo(BigDecimal.ZERO) == 0) {
            System.out.println("⚠️ [SYSTEM] Plantilla sin coste salarial. Nóminas canceladas.");
            return;
        }

        // 3. Crear el paquete de datos simulando una petición externa
        // IMPORTANTE: Ponemos el salario en NEGATIVO porque es un gasto para el club.
        // Asumimos que el budgetId 1 es el presupuesto general.
        TransactionRequestDTO payrollRequest = new TransactionRequestDTO(
                totalPayroll.negate(),
                "EXPENSE", // Asumiendo que requiere descripción
                "Pago de nóminas automatizado", // Asumiendo que requiere descripción
                1L // Presupuesto general
        );

        // 4. Delegamos el guardado al TransactionService (Reutilización de código)
        try {
            transactionService.createTransaction(payrollRequest);
            System.out.println("✅ [SYSTEM] Nóminas pagadas: -" + totalPayroll + "€ extraídos del presupuesto.");
        } catch (Exception e) {
            System.err.println("❌ [ERROR] Fallo al pagar nóminas: " + e.getMessage());
        }
    }
}

