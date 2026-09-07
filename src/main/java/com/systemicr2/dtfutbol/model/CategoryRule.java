package com.systemicr2.dtfutbol.model;

import com.systemicr2.dtfutbol.model.enums.CategoryLevel;
import com.systemicr2.dtfutbol.model.enums.Modality;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "category_rules")
@Getter
@Setter
public class CategoryRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique =true)
    private String name; // Ej: "Benjamin B", "Juvenil Preferente"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Modality modality; //F7, F11, FUTSAL

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryLevel level; // youth, amateur, professional

    //Propiedades dinámicas del motor de reglas
    private int numberOfPeriods;
    private int periodDurationMinutes; // Duración de la parte (ej: 20 min Benjamines, 45 min F11)
    private boolean flyingSubstitutions; // Cambios volantes permitidos (típico en fútbol base/fútbol sala)
}
