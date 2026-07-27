package com.systemicr2.dtfutbol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity // Le dice a Spring Data JPA: "Esta clase es una tabla en la base de datos MySQL"
@Data   // Le dice a Lombok: "Genera todos los getters, setters y constructores automáticamente por detrás"
@Table(name = "players") // Forzamos a que la tabla en MySQL se llame en plural
public class Player {

    @Id // Marca este campo como la Primary Key de la tabla (Complejidad O(1) en búsquedas directas)
    @Column(nullable = false, unique = true)

    @NotBlank(message = "El DNI es obligatorio")
    private String dni;

    @Column(nullable = false)

    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotBlank(message = "La posición es obligatoria")
    private String position;

    @Min(value=1, message="Camiseta > 0")
    private Integer shirtNumber;

    // ... tus otros campos (id, name, position, etc)

    private Double marketValue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")

    private Team team;

    // ... getters y setters actualizados
    }