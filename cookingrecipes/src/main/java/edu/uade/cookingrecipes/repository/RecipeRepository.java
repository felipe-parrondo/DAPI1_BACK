package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.entity.Recipe;
import edu.uade.cookingrecipes.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {
    List<Recipe> findTop3ByApprovedTrueOrderByIdDesc();
    List<Recipe> findByUser_Id(Long userId);
}
