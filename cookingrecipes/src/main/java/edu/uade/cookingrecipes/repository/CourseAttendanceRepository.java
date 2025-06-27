package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.entity.CourseAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseAttendanceRepository extends JpaRepository<CourseAttendance, Long> {
    List<CourseAttendance> findByUserIdAndCourseId(Long userId, Long courseId);
    List<CourseAttendance> findByCourseId(Long courseId);
}
