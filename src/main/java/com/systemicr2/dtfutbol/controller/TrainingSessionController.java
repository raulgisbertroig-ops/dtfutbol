package com.systemicr2.dtfutbol.controller;

import com.systemicr2.dtfutbol.model.TrainingSession;
import com.systemicr2.dtfutbol.service.TrainingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainings")
public class TrainingSessionController {

    // 1. Conexión lógica con la capa de Servicio
    @Autowired
    private TrainingSessionService trainingSessionService;

    // 2. mapeo de Mutación (Escritura de BD)
    @PostMapping
    public ResponseEntity<TrainingSession> createTrainingSession(@RequestBody TrainingSession trainingSession) {
        TrainingSession newSession = trainingSessionService.createTrainingSession(trainingSession);
        return new ResponseEntity<>(newSession, HttpStatus.CREATED); // Devuelve el código 201
    }

    // 3. Mapeo de Extracción (Lectura de BD)
    @GetMapping
    public ResponseEntity<List<TrainingSession>> getAllTrainingSessions() {
        List<TrainingSession> sessions = trainingSessionService.getAllTrainingSessions();
        return new ResponseEntity<>(sessions, HttpStatus.OK); // Devuelve el codigo 200
    }
}
