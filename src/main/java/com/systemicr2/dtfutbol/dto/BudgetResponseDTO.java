package com.systemicr2.dtfutbol.dto;

import java.math.BigDecimal;

// paquete de red DEVOLVER datos procesados al usuario
public record BudgetResponseDTO(
    Long id,
    String category,
    BigDecimal totalAmount

) {}


