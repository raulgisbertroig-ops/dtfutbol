package com.systemicr2.dtfutbol.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "training_sessions")
@Getter
@Setter

public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String objective;

    // Obligamos al motor relacional a trataar esto como un objeto temporal real
    private LocalDate date;

    private int durationMinutes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Team team;

    // Spring Boot necesita un constructor vacío por defecto para instanciar la clase en memoria.
    public TrainingSession(){

    }

}