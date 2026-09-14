package com.systemicr2.dtfutbol.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
@Getter
@Setter
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tu decisión: Opción B ejecutada
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_id", nullable = false)
    private Team homeTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_team_id", nullable = false)
    private Team awayTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_rule_id", nullable = false)
    private CategoryRule categoryRule;

    @Column(nullable = false)
    private LocalDateTime matchDate;

    // Propagación para evitar huérfanos
    // @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    // private java.util.List<MatchEvent> events;

    // Relación bidireccional: Un partido tiene una lista de convocatorias
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Callup> callups;

}