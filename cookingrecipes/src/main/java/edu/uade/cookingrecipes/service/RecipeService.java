package edu.uade.cookingrecipes.service;

import edu.uade.cookingrecipes.dto.request.RecipeRequestDto;
import edu.uade.cookingrecipes.dto.response.RecipeResponseDto;
import org.springframework.web.multipart.MultipartFile;
import edu.uade.cookingrecipes.dto.response.IngredientResponseDto;

import java.util.List;

public interface RecipeService {
    List<RecipeResponseDto> getAllRecipes();
    List<RecipeResponseDto> filterRecipes(String dishType, String order, String ingredient, String sortByDate, String username);
    RecipeResponseDto createRecipe(RecipeRequestDto recipeRequestDto, List<MultipartFile> files);
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
