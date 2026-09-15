package com.systemicr2.dtfutbol.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamStandingDto {

    private String teamName;
    private int matchesPlayed;
    private int wins;
    private int losses;
    private int draws;
    private int goalsFor;
    private int goalsAgainst;
    private int points;

    // Custom constructor to easily create a new row for a team.
    public TeamStandingDto(String teamName) {
        this.teamName = teamName;
        this.matchesPlayed = 0;
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
        this.goalsFor = 0;
        this.goalsAgainst = 0;
        this.points = 0;
    }
}

