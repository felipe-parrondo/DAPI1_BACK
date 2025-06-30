package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    Optional<AttendanceRecord> findByCourseIdAndUserIdAndDate(Long courseId, Long userId, LocalDate date);
    List<AttendanceRecord> findAllByDateAndCountedFalse(LocalDate date);
    List<AttendanceRecord> findByCourseIdAndUserId(Long courseId, Long userId);
}
