package com.systemicr2.dtfutbol.dto;

import java.math.BigDecimal;

public record TransactionResponseDTO(
        Long id,
        BigDecimal amount,
        Long budgetId
) {}