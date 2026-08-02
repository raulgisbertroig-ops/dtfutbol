package com.systemicr2.dtfutbol.service;

import com.systemicr2.dtfutbol.model.TrainingSession;
import com.systemicr2.dtfutbol.repository.TrainingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingSessionService {

    @Autowired
    private TrainingSessionRepository trainingSessionRepository;

    // Método de Escritura (Mutación de estado)
    public TrainingSession createTrainingSession(TrainingSession session) {
        return trainingSessionRepository.save(session);
    }

    // Método de Lectura (Extracción de datos)
    public List<TrainingSession> getAllTrainingSessions() {
        return trainingSessionRepository.findAll();
    }
}