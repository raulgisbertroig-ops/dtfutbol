package com.systemicr2.dtfutbol.model;

import com.systemicr2.dtfutbol.model.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="training_attendance")
@Data
@NoArgsConstructor
public class TrainingAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // WHICH training session?
    @ManyToOne
    @JoinColumn(name = "training_session_id", nullable = false)
    private TrainingSession trainingSession;

    // WHICH player?
    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    // DID THEY ATTEND? (PRESENT, ABSENT, EXCUSED, INJURED)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus status;

    // WHY (Free text for excused like "School exam" or "health")
    private String notes;
}
