package edu.uade.cookingrecipes.mapper;

import edu.uade.cookingrecipes.entity.Recipe;
import edu.uade.cookingrecipes.entity.RecipeList;
import edu.uade.cookingrecipes.dto.request.ListRequestDto;
import edu.uade.cookingrecipes.dto.response.ListResponseDto;
import edu.uade.cookingrecipes.dto.response.RecipeResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class ListMapper {
    public static ListResponseDto toDto(RecipeList entity) {
        if (entity == null) return null;

        java.util.List<RecipeResponseDto> recipes = entity.getRecipes()
                .stream()
                .map(RecipeMapper::toDto)
                .collect(Collectors.toList());

        return new ListResponseDto(
                entity.getId(),
                entity.getName(),
                recipes
        );
    }

    public static RecipeList toEntity(ListRequestDto dto, List<Recipe> recipes) {
        if (dto == null) return null;

        RecipeList entity = new RecipeList();
        entity.setName(dto.getName());
        entity.setRecipes(recipes);
        return entity;
    }
}
