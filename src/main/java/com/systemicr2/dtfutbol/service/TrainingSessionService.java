package com.systemicr2.dtfutbol.service;

import com.systemicr2.dtfutbol.dto.TrainingRequestDTO;
import com.systemicr2.dtfutbol.dto.TrainingResponseDTO;
import com.systemicr2.dtfutbol.model.Team;
import com.systemicr2.dtfutbol.model.TrainingSession;
import com.systemicr2.dtfutbol.repository.TeamRepository;
import com.systemicr2.dtfutbol.repository.TrainingSessionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TrainingSessionService {

    // Variable final (inmutable)
    private final TrainingSessionRepository trainingSessionRepository;
    // 1. Añadimos la dependencia hacia la tabla de equipos
    private final TeamRepository teamRepository;

    // 2. Actuaslizamos el constructor para inyectar ambas dependencias
    // Inyecion por constructor (Estándar actual)
    public TrainingSessionService(TrainingSessionRepository trainingSessionRepository, TeamRepository teamRepository) {
        this.trainingSessionRepository = trainingSessionRepository;
        this.teamRepository = teamRepository;
    }

    // Mapeo de Escritura (Mutación de BD con DTOs)
    public TrainingResponseDTO createTrainingSession(TrainingRequestDTO requestDTO) {
        // 4. Buscamos el equipo real en el disco duro mediante el ID proporcionado en el DTO
        Team realTeam = teamRepository.findById(requestDTO.teamId())
                .orElseThrow(() -> new RuntimeException("Error: El equipo con ID " + requestDTO.teamId() + " no existe."));

        // 5. Mapeo Manual: DTO (Entrada) -> Entidad (JPA)
        TrainingSession newSession = new TrainingSession();
        newSession.setDate(requestDTO.date());
        newSession.setDurationMinutes(requestDTO.durationMinutes());
        newSession.setObjective(requestDTO.Objective());
        newSession.setTeam(realTeam);

        // 6. Guardamos con seguridad en MySQL
        TrainingSession savedSession = trainingSessionRepository.save(newSession);

        // 7. Mapeo Manual: Entidad (JPA) -> DTO (Salida) para no exponer el modelo
        return new TrainingResponseDTO(
                savedSession.getId(),
                savedSession.getDate(),
                savedSession.getDurationMinutes(),
                savedSession.getObjective(),
                savedSession.getTeam().getName()
        );
    }

    // Mapeo de Actualización (Sobrescritura en BD)
    public TrainingResponseDTO updateTrainingSession(Long id, TrainingRequestDTO requestDTO) {

        // 1. Verificamos que el entrenamiento original existe.
        TrainingSession existingSession = trainingSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: El equipo con ID " + id + " no existe."));

        // 2. Verificamos que el nuevo equipo asignado tambien existe
        Team realTeam = teamRepository.findById(requestDTO.teamId())
                .orElseThrow(() -> new RuntimeException("Error: El equipo con ID " + requestDTO.teamId() + " no existe."));

        // 3. Mutuación de estado RAM
        existingSession.setDate(requestDTO.date());
        existingSession.setDurationMinutes(requestDTO.durationMinutes());
        existingSession.setObjective(requestDTO.Objective());
        existingSession.setTeam(realTeam);

        // 4. Persistencia en disco
        TrainingSession updatedSession = trainingSessionRepository.save(existingSession);

        // 5. Retorno aplanado para evitar fuga de abstracción
        return new TrainingResponseDTO(
                updatedSession.getId(),
                updatedSession.getDate(),
                updatedSession.getDurationMinutes(),
                updatedSession.getObjective(),
                updatedSession.getTeam().getName()
        );
    }


    public Page<TrainingResponseDTO> getAllTrainingSessions(Pageable pageable) {
        Page<TrainingSession> sessionsPage = trainingSessionRepository.findAllWithTeams(pageable);

        return sessionsPage.map(session -> new TrainingResponseDTO(
                session.getId(),
                session.getDate(),
                session.getDurationMinutes(),
                session.getObjective(),
                session.getTeam().getName()
        ));
    }

    // Mapeo de Desrtrucción (Purga de BD)
    public void deleteTrainingSession(Long id) {
        // 1. Verificamos la existencia antes de intentar borrar
        if (!trainingSessionRepository.existsById(id)) {
            throw new RuntimeException("Error: No se puede borrar. La sesión con ID" + id + " no existe.");
        }
        // 2. Ejecución de purga física
        trainingSessionRepository.deleteById(id);

        }
    }

