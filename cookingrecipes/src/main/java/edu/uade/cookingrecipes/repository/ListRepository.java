package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.Entity.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListRepository extends JpaRepository<List, Long> {
}
