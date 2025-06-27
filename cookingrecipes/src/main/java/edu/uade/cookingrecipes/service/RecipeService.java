package edu.uade.cookingrecipes.service;

import edu.uade.cookingrecipes.Entity.Embeddable.IngredientEmbeddable;
import edu.uade.cookingrecipes.dto.Request.RecipeRequestDto;
import edu.uade.cookingrecipes.dto.Response.RecipeResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecipeService {
    List<RecipeResponseDto> getAllRecipes();
    List<RecipeResponseDto> filterRecipes(String dishType, String order, String ingredient, String sortByDate, String username);
    RecipeResponseDto createRecipe(RecipeRequestDto recipeRequestDto, List<MultipartFile> files);
    boolean approveRecipe(Long recipeId);
    List<RecipeResponseDto> getRecentRecipes();
    boolean deleteRecipe(Long recipeId);
    RecipeResponseDto getRecipeById(Long recipeId);
    List<String> getRecipeIngredients(Long recipeId);
    List<String> getRecipeDishTypes(Long recipeId);
}
