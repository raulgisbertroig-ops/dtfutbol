package com.systemicr2.dtfutbol.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "club")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer foundationYear;
    private String contactEmail;

    // Relación 1:N -> Un Club tiene muchos Equipos (Teams)
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Team> teams;

    public Club() {}

    public Club(String name, Integer foundationYear, String contactEmail) {
        this.name = name;
        this.foundationYear = foundationYear;
        this.contactEmail = contactEmail;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getFoundationYear() { return foundationYear; }
    public void setFoundationYear(Integer foundationYear) { this.foundationYear = foundationYear; }
    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
    public List<Team> getTeams() { return teams; }
    public void setTeams(List<Team> teams) { this.teams = teams; }
}