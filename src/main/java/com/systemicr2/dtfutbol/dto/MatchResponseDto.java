package com.systemicr2.dtfutbol.dto;

import lombok.Data;

@Data
public class MatchResponseDto {
    private Long id;
    private Integer homeGoals;
    private Integer awayGoals;
}
