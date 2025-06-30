package edu.uade.cookingrecipes.service;

import org.springframework.data.domain.Pageable;
import edu.uade.cookingrecipes.entity.Recipe;
import org.springframework.data.jpa.domain.Specification;
import edu.uade.cookingrecipes.dto.request.RecipeRequestDto;
import edu.uade.cookingrecipes.dto.response.IngredientResponseDto;
import edu.uade.cookingrecipes.dto.response.RecipeResponseDto;

import java.util.List;

public interface RecipeService {
    List<RecipeResponseDto> getAllRecipes();
    List<RecipeResponseDto> filterRecipes(Specification<Recipe> spec, Pageable pageable);
    RecipeResponseDto createRecipe(RecipeRequestDto recipeRequestDto);
    Long validateRecipe(String recipeName);
    boolean approveRecipe(Long recipeId);
    List<RecipeResponseDto> getRecentRecipes();
    boolean deleteRecipe(Long recipeId);
    RecipeResponseDto getRecipeById(Long recipeId);
    List<String> getRecipeIngredients(Long recipeId);
    List<String> getRecipeDishTypes(Long recipeId);
    List<String> getAllDishTypes();
    List<IngredientResponseDto> getFullIngredientsByRecipeId(Long recipeId);

}
