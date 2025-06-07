package edu.uade.cookingrecipes.service;

import edu.uade.cookingrecipes.dto.Request.ListRequestDto;
import edu.uade.cookingrecipes.dto.Response.ListResponseDto;

import java.util.List;

public interface ListService {
    List<ListResponseDto> getAllLists();
    ListResponseDto createList(ListRequestDto requestDto);
    ListResponseDto addRecipeToList(Long listId, Long recipeId);
    ListResponseDto removeRecipeFromList(Long listId, Long recipeId);
    boolean deleteList(Long listId);
}
