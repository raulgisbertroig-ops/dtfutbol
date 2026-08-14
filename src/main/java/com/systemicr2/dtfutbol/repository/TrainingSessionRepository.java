package com.systemicr2.dtfutbol.repository;

import com.systemicr2.dtfutbol.model.TrainingSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {
    // El Scope queda vacío. Spring inyecta la implementación en tiempo de ejecución (Proxy).

    // Extrae sus sesiones y sus equipos asociados en una única consulta SQL optimizada.
    @Query(value = "SELECT t FROM TrainingSession t JOIN FETCH t.team",
            countQuery = "SELECT COUNT (t) FROM TrainingSession t")
    Page<TrainingSession> findAllWithTeams(Pageable pageable);

}