package com.systemicr2.dtfutbol.controller;


import com.systemicr2.dtfutbol.service.TrainingSessionService;
import com.systemicr2.dtfutbol.dto.TrainingRequestDTO;
import com.systemicr2.dtfutbol.dto.TrainingResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/trainings")
public class TrainingSessionController {

    // 1. Variable final (Inmutable)
    private final TrainingSessionService trainingSessionService;

    // 2. Inyector por constructor (obligatorio)
    public TrainingSessionController(TrainingSessionService trainingSessionService) {
        this.trainingSessionService = trainingSessionService;
    }

    // 3. Mapeo de Mutación (Escritura de BD con Validación)
    @PostMapping
    public ResponseEntity<TrainingResponseDTO> createTrainingSession(@Valid @RequestBody TrainingRequestDTO requestDTO) {
        TrainingResponseDTO createdSession = trainingSessionService.createTrainingSession(requestDTO);
        return new ResponseEntity<>(createdSession, HttpStatus.CREATED);
    }

    // 4. Mapeo de Extracción (Lectura de BD)
    @GetMapping
    public ResponseEntity<Page<TrainingResponseDTO>> getAllTrainingSessions(
            @PageableDefault Pageable pageable) {

        Page<TrainingResponseDTO> sessionsPage = trainingSessionService.getAllTrainingSessions(pageable);
        return new ResponseEntity<>(sessionsPage, HttpStatus.OK);
    }

    // 5. Mapeo de Actualizacion (Sobrescritura en BD)
    @PutMapping("/{id}")
    public ResponseEntity<TrainingResponseDTO> updateTrainingSession(
            @PathVariable Long id,
            @Valid @RequestBody TrainingRequestDTO requestDTO) {

        TrainingResponseDTO updatedSession = trainingSessionService.updateTrainingSession(id, requestDTO);
        return new ResponseEntity<>(updatedSession, HttpStatus.OK);
    }
    // 6. Mapeo de Sustracción (Eliminación de BD)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainingSession(@PathVariable Long id) {
        trainingSessionService.deleteTrainingSession(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
