package com.systemicr2.dtfutbol.repository;

import com.systemicr2.dtfutbol.model.TrainingAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingAttendanceRepository extends JpaRepository<TrainingAttendance, Long> {

    // We will leave this empty for now. We can add custom JPQL queries later if needed!

}
