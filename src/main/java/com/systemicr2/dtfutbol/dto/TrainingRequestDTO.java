package com.systemicr2.dtfutbol.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record TrainingRequestDTO(
        @NotNull(message = "Error: La fecha de la sessión no puede ser nula.")
        LocalDate date,

        @NotNull(message = "Error: La duración es un campo obligatorio.")
        @Min(value = 1, message = "Error: La duración algorítmica mínima es de 1 minuto.")
        Integer durationMinutes,

        @NotBlank(message = "Error: El objetivo del entrenamiento no puede ser una cadena vacía.")
        String objective,

        @NotNull(message = "Error: El ID del equipo a vincular es estrictamente necesario.")
        Long teamId
) {
}


