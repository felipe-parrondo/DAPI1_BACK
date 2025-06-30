package edu.uade.cookingrecipes.service;

import edu.uade.cookingrecipes.entity.Recipe;
import org.springframework.data.jpa.domain.Specification;
import edu.uade.cookingrecipes.dto.request.RecipeRequestDto;
import edu.uade.cookingrecipes.dto.response.RecipeResponseDto;
import org.springframework.web.multipart.MultipartFile;
import edu.uade.cookingrecipes.dto.response.IngredientResponseDto;

import java.util.List;

public interface RecipeService {
    List<RecipeResponseDto> getAllRecipes();
    List<RecipeResponseDto> filterRecipes(Specification<Recipe> spec, String sort);
    RecipeResponseDto createRecipe(RecipeRequestDto recipeRequestDto, List<MultipartFile> files);
    Long validateRecipe(String recipeName);
    boolean approveRecipe(Long recipeId, Boolean isApproved);
    List<RecipeResponseDto> getRecentRecipes();
    boolean deleteRecipe(Long recipeId);
    RecipeResponseDto getRecipeById(Long recipeId);
    List<String> getRecipeIngredients(Long recipeId);
    List<String> getRecipeDishTypes(Long recipeId);
    List<String> getAllDishTypes();
    List<IngredientResponseDto> getFullIngredientsByRecipeId(Long recipeId);
    void rejectRecipesByUserId(Long userId);
}
