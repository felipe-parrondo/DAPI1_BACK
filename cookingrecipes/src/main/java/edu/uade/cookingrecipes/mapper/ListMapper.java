package edu.uade.cookingrecipes.mapper;

import edu.uade.cookingrecipes.Entity.Recipe;
import edu.uade.cookingrecipes.Entity.List;
import edu.uade.cookingrecipes.dto.Request.ListRequestDto;
import edu.uade.cookingrecipes.dto.Response.ListResponseDto;
import edu.uade.cookingrecipes.dto.Response.RecipeResponseDto;

import java.util.stream.Collectors;

public class ListMapper {
    public static ListResponseDto toDto(List entity) {
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

    public static List toEntity(ListRequestDto dto, java.util.List<Recipe> recipes) {
        if (dto == null) return null;

        List entity = new List();
        entity.setName(dto.getName());
        entity.setRecipes(recipes);
        return entity;
    }
}
