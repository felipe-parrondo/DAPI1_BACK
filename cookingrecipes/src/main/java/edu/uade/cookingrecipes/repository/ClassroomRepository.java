package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long>{
    Classroom findBySiteId(Long siteId);
}
