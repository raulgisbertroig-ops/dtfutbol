package com.systemicr2.dtfutbol.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "callups")
@Getter
@Setter
public class Callup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con el partido
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    @JsonIgnore
    private Match match;

    // Relación con el jugador
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    @JsonIgnore
    private Player player;

    // Datos estadisticos de la convocatoria
    @Column(nullable = false)
    private boolean isStarter; // ¿Fue titular? (true/false)

    @Column(nullable = false)
    private int minutesPlayed; // ¿Cuantos minutos jugó?
}