package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.Entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findTop10ByOrderByIdDesc();

}
