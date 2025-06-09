package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.Entity.RecipeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListRepository extends JpaRepository<RecipeList, Long> {
}
