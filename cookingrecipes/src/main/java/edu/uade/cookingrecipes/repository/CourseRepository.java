package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<List<Course>> findBySite_Id(Long siteId);
}
