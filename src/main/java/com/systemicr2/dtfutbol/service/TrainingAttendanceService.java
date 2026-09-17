package com.systemicr2.dtfutbol.service;

import com.systemicr2.dtfutbol.model.Player;
import com.systemicr2.dtfutbol.model.TrainingAttendance;
import com.systemicr2.dtfutbol.model.TrainingSession;
import com.systemicr2.dtfutbol.model.enums.AttendanceStatus;
import com.systemicr2.dtfutbol.repository.TrainingAttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingAttendanceService {

    private final TrainingAttendanceRepository attendanceRepository;

    public TrainingAttendance markAttendance(TrainingSession session, Player player, AttendanceStatus status, String notes) {

        TrainingAttendance record = new TrainingAttendance();
        record.setTrainingSession(session);
        record.setPlayer(player);
        record.setStatus(status);
        record.setNotes(notes);

        return attendanceRepository.save(record);
    }
    public List<TrainingAttendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }
}

