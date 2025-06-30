package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.entity.RecipeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListRepository extends JpaRepository<RecipeList, Long> {
    List<RecipeList> findAllByUserId(Long id);
    Optional<List<RecipeList>> findByUser_IdAndRecipes_Id(Long id, Long recipeId);
}