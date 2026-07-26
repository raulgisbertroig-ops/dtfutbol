package com.systemicr2.dtfutbol.service;

import com.systemicr2.dtfutbol.model.Team;
import com.systemicr2.dtfutbol.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException; // Importación crítica

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public Team createTeam(Team team) {
        // Lógica Anti-IA: Consultar si existe antes de guardar
        if (teamRepository.existsByName(team.getName())) {
            // Abortamos la operación y lanzamos un error 409 controlado
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Violación de integridad: El equipo '" + team.getName() + "' ya existe."
            );
        }

        return teamRepository.save(team);
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }
}