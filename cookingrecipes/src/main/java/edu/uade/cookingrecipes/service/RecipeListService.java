package edu.uade.cookingrecipes.service;

import edu.uade.cookingrecipes.dto.Request.RecipeListRequestDto;
import edu.uade.cookingrecipes.dto.Response.RecipeListResponseDto;

import java.util.List;

public interface RecipeListService {
    List<RecipeListResponseDto> getAllLists();
    RecipeListResponseDto createList(RecipeListRequestDto requestDto);
    RecipeListResponseDto addRecipeToList(Long listId, Long recipeId);
    RecipeListResponseDto removeRecipeFromList(Long listId, Long recipeId);
    boolean deleteList(Long listId);
}
