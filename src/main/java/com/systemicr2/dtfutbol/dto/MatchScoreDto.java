package com.systemicr2.dtfutbol.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class MatchScoreDto {

    @NotNull(message = "Home goals cannot be null")
    @Min(value = 0, message = "Goals cannot be negative")
    private Integer homeGoals;

    @NotNull(message = "Away goals cannot be null")
    @Min(value = 0, message = "Goals cannot be negative")
    private Integer awayGoals;
}

