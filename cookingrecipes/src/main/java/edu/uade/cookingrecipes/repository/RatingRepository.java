package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByRecipeId(Long recipeId);
    Long findRecipeIdById(Long ratingId);
    List<Rating> findByUser_Id(Long userId);
    List<Rating> findByApproved(Boolean approved);
}