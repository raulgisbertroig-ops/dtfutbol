package com.systemicr2.dtfutbol.dto;

import java.time.LocalDate;

public record TrainingResponseDTO(
        Long id,
        LocalDate date,
        Integer durationMinutes,
        String objective,
        String teamName
) {
}