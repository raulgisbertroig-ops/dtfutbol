package com.systemicr2.dtfutbol.dto;

import java.math.BigDecimal;

public record TransactionRequestDTO(
        java.math.BigDecimal amount,
        String type,
        String description,
        Long budgetId // Clave foranea virtual que recibimos por red
) {}

