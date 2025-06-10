package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.Entity.Rating;
import edu.uade.cookingrecipes.Entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByRecipeId(Long recipeId);
    Long findRecipeIdById(Long ratingId);
}
