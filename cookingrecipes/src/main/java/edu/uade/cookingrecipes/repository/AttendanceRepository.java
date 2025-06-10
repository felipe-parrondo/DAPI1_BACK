package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.Entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByUserIdAndCourseId(Long userId, Long courseId);
}
