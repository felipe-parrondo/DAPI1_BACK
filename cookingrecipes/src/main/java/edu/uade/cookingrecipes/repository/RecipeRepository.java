package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.Entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
