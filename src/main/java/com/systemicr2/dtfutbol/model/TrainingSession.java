package com.systemicr2.dtfutbol.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity

@Data

@Table(name = "training_sessions")

public class TrainingSession {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String objective;

    private String date;

}