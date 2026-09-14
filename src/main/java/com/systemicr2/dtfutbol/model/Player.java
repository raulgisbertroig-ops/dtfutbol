package com.systemicr2.dtfutbol.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity // Le dice a Spring Data JPA: "Esta clase es una tabla en la base de datos MySQL"
@Data   // Le dice a Lombok: "Genera todos los getters, setters y constructores automáticamente por detrás"
@Table(name = "players") // Forzamos a que la tabla en MySQL se llame en plural
public class Player {

    @Id // Marca este campo como la Primary Key de la tabla (Complejidad O(1) en búsquedas directas)
    @Column(nullable = false, unique = true)

    @NotBlank(message = "El DNI es obligatorio")
    private String dni;

    @Column(name = "salary", precision = 10, scale = 2)
    private BigDecimal monthlySalary;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotBlank(message = "La posición es obligatoria")
    private String position;

    @Min(value = 1, message = "Camiseta > 0")
    private Integer shirtNumber;

    // ... tus otros campos (id, name, position, etc)

    private Double marketValue;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    @JsonIgnore

    private Team team;

    // Historial de convocatorias del jugador
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Callup> callups;

    @ManyToMany
    @JoinTable(
            name = "player_training",
            joinColumns = @JoinColumn(name = "training_player_id"),
            inverseJoinColumns = @JoinColumn(name = "training_session_id")
    )

    @JsonIgnore
    private List<TrainingSession> trainingSessions = new java.util.ArrayList<>();

    // --- ESTADISTICAS (MVP) ---
    private Integer matchesPlayed = 0; // Valor por defeccto 0 al crear el jugador
    private Integer goalsScored = 0;
    private Integer assists = 0;
}
    // ... getters y setters actualizados

