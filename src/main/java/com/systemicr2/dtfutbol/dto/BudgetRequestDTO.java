package com.systemicr2.dtfutbol.dto;

import java.math.BigDecimal;

// Paquete de red para RECIBIR datos de usuario
public record BudgetRequestDTO(
        String category,
        BigDecimal totalAmount
) {}
