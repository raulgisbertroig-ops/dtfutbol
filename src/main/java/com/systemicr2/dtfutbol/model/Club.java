package com.systemicr2.dtfutbol.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "club")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "foundation_year")
    private Integer foundationYear;

    @Column(name = "contact_email", length = 150)
    private String contactEmail;

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