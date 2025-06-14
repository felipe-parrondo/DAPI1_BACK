package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.Entity.Recipe;
import edu.uade.cookingrecipes.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findTop3ByOrderByIdDesc();
    boolean existsByNameAndUser(String name, UserModel user);
}
