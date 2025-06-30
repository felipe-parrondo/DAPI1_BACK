package edu.uade.cookingrecipes.service;

import org.springframework.data.domain.Pageable;
import edu.uade.cookingrecipes.Entity.Recipe;
import edu.uade.cookingrecipes.dto.Request.RecipeRequestDto;
import edu.uade.cookingrecipes.dto.Response.IngredientResponseDto;
import edu.uade.cookingrecipes.dto.Response.RecipeResponseDto;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface RecipeService {
    List<RecipeResponseDto> getAllRecipes();
    List<RecipeResponseDto> filterRecipes(Specification<Recipe> spec, Pageable pageable);
    RecipeResponseDto createRecipe(RecipeRequestDto recipeRequestDto);
    boolean approveRecipe(Long recipeId);
    List<RecipeResponseDto> getRecentRecipes();
    boolean deleteRecipe(Long recipeId);
    RecipeResponseDto getRecipeById(Long recipeId);
    List<String> getRecipeIngredients(Long recipeId);
    List<String> getRecipeDishTypes(Long recipeId);
    List<IngredientResponseDto> getFullIngredientsByRecipeId(Long recipeId);

}
