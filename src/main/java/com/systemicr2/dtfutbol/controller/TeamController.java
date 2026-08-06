package com.systemicr2.dtfutbol.controller;

import com.systemicr2.dtfutbol.model.Team;
import com.systemicr2.dtfutbol.service.TeamService;
import com.systemicr2.dtfutbol.service.TeamFinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;
    private final TeamFinancialService financialService;

    // Inyección de dependencias unificada por constructor
    public TeamController(TeamService teamService, TeamFinancialService financialService) {
        this.teamService = teamService;
        this.financialService = financialService;
    }

    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        Team savedTeam = teamService.createTeam(team);
        return new ResponseEntity<>(savedTeam, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody Team team) {
        return new ResponseEntity<>(teamService.updateTeam(id, team), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        return new ResponseEntity<>(teamService.getAllTeams(), HttpStatus.OK);
    }

    // Nuevo endpoint de lógica financiera
    @GetMapping("/{teamId}/financial/can-afford")
    public ResponseEntity<Boolean> checkAffordability(
            @PathVariable Long teamId,
            @RequestParam BigDecimal playerSalary,
            @RequestParam BigDecimal budgetLimit) {

        boolean canAfford = financialService.canAffordNewPlayer(teamId, playerSalary, budgetLimit);
        return ResponseEntity.ok(canAfford);
    }
}