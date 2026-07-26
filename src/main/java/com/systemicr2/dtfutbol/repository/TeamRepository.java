package com.systemicr2.dtfutbol.repository;

import com.systemicr2.dtfutbol.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    // Spring Boot leerá este nombre y creará un "SELECT count(*) WHERE name = ?"
    boolean existsByName(String name);
}