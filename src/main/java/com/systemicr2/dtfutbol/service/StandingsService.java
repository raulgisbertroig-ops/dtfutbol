package com.systemicr2.dtfutbol.service;

import com.systemicr2.dtfutbol.dto.TeamStandingDto;
import com.systemicr2.dtfutbol.model.Match;
import com.systemicr2.dtfutbol.model.Team;
import com.systemicr2.dtfutbol.repository.MatchRepository;
import com.systemicr2.dtfutbol.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class StandingsService {

    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;

    public List<TeamStandingDto> calculateLeagueStandings() {
        List<Team> allTeams = teamRepository.findAll();
        List<Match> allMatches = matchRepository.findAll();

        List<TeamStandingDto> standings = new ArrayList<>();

        for (Team team : allTeams) {
            TeamStandingDto dto = new TeamStandingDto(team.getName());

            for (Match match : allMatches) {
                //Only process the match if it has a final score (NOT NULL)
                if (match.getHomeGoals() != null && match.getAwayGoals() != null) {

                    if (match.getHomeTeam().getId().equals(team.getId())) {
                        processMatchResult(dto, match.getHomeGoals(), match.getAwayGoals());
                    }
                    else if (match.getAwayTeam().getId().equals(team.getId())) {
                        processMatchResult(dto, match.getAwayGoals(), match.getHomeGoals());
                    }
                }
            }
            standings.add(dto);
        }

        //Sort the table by points (Highest to lowest)
        standings.sort((t1, t2) -> Integer.compare(t2.getPoints(), t1.getPoints()));

        return standings;
    }

    // A helper method to keep the main code clean and readable
    private void processMatchResult(TeamStandingDto dto, int goalsFor, int goalsAgainst) {
        dto.setMatchesPlayed(dto.getMatchesPlayed() + 1);
        dto.setGoalsFor(dto.getGoalsFor() + goalsFor);
        dto.setGoalsAgainst(dto.getGoalsAgainst() + goalsAgainst);

        if (goalsFor > goalsAgainst) {
            dto.setWins(dto.getWins() + 1);
            dto.setPoints(dto.getPoints() + 3);
        } else if (goalsFor == goalsAgainst) {
            dto.setDraws(dto.getDraws() + 1);
            dto.setPoints(dto.getPoints() + 1);
        } else {
            dto.setLosses(dto.getLosses() + 1);
        }

    }
}

