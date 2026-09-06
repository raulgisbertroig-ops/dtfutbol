package com.systemicr2.dtfutbol.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "teams")
@Getter
@Setter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank(message = "El nombre del equipo es obligatorio")
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    // Relación T-51: Integración con el Motor Polimórfico (Reglas Tácticas)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_rule_id", nullable = false)
    private CategoryRule categoryRule;

}