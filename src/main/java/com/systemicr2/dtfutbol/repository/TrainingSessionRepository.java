package com.systemicr2.dtfutbol.repository;

import com.systemicr2.dtfutbol.model.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {
    // El Scope queda vacío. Spring inyecta la implementación en tiempo de ejecución (Proxy).
}