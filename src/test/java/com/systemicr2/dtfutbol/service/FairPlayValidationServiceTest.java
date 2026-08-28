package com.systemicr2.dtfutbol.service;

import com.systemicr2.dtfutbol.exception.SalaryCapExceededException;
import com.systemicr2.dtfutbol.model.Team;
import com.systemicr2.dtfutbol.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FairPlayValidationServiceTest {

    @Mock
    private TeamRepository teamRepository; // Simulamos la base de datos

    @InjectMocks
    private FairPlayValidationService validationService; // El servicio real que estamos probando

    @Test
    void whenBudgetIsSufficient_thenTransferIsValidated() {
        // 1. ARRANGE (Preparar los datos)
        Long teamId = 1L;
        Team mockTeam = new Team();
        mockTeam.setId(teamId);
        mockTeam.setBudget(new BigDecimal("100000"));
        mockTeam.setCurrentWageBill(new BigDecimal("50000"));

        // Le decimos al Mock qué debe responder cuando el servicio pregunte por el equipo 1
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(mockTeam));

        BigDecimal newPlayerSalary = new BigDecimal("20000");

        // 2 & 3. ACT & ASSERT (Ejecutar y Comprobar)
        // 50k (actual) + 20k (nuevo) = 70k. Es menor que 100k. NO debe lanzar excepción.
        assertDoesNotThrow(() -> validationService.validateTransfer(teamId, newPlayerSalary));

    }

    @Test
    void whenBudgetIsExceeded_thenThrowsException() {
        // 1. ARRANGE (Preparar un equipo al borde de la quiebra)
        Long teamId = 1L;
        Team mockTeam = new Team();
        mockTeam.setId(teamId);
        mockTeam.setBudget(new BigDecimal("100000")); // Presupiuesto total: 100k
        mockTeam.setCurrentWageBill(new BigDecimal("90000")); // Gastado: 90k (solo quedan 10k)

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(mockTeam));

        // Intentamos fichar a alguien que cobra 20k
        BigDecimal newPlayerSalary = new BigDecimal("20000");

        // 2 & 3. ACT & ASSERT (Afirmamos que ESTO va a lanzar una excepción)
        // 90k + 20k = 110k. Supera los 100k. DEBE explotar.
        assertThrows(SalaryCapExceededException.class,
                () -> validationService.validateTransfer(teamId, newPlayerSalary));
    }
}

