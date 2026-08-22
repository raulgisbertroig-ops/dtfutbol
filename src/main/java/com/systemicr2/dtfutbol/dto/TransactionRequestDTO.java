package com.systemicr2.dtfutbol.dto;

import java.math.BigDecimal;

public record TransactionRequestDTO(
        BigDecimal amount,
        Long budgetId // Clave foranea virtual que recibimos por red
) {}

