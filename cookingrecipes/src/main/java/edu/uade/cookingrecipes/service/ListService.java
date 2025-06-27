package edu.uade.cookingrecipes.service;

import edu.uade.cookingrecipes.dto.request.ListRequestDto;
import edu.uade.cookingrecipes.dto.response.ListResponseDto;

import java.util.List;

public interface ListService {
    List<ListResponseDto> getAllLists();
    ListResponseDto createList(ListRequestDto requestDto);
    ListResponseDto addRecipeToList(Long listId, Long recipeId);
    ListResponseDto removeRecipeFromList(Long listId, Long recipeId);
    boolean deleteList(Long listId);
    ListResponseDto getListById(Long listId);
    List<ListResponseDto> getListsByRecipeId (Long recipeId);
}
