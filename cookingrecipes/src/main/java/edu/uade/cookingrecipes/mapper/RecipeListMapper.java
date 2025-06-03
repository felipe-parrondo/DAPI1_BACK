package edu.uade.cookingrecipes.mapper;

import edu.uade.cookingrecipes.Entity.Recipe;
import edu.uade.cookingrecipes.Entity.RecipeList;
import edu.uade.cookingrecipes.dto.Request.RecipeListRequestDto;
import edu.uade.cookingrecipes.dto.Response.RecipeListResponseDto;
import edu.uade.cookingrecipes.dto.Response.RecipeResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeListMapper {
    public static RecipeListResponseDto toDto(RecipeList entity) {
        if (entity == null) return null;

        List<RecipeResponseDto> recipes = entity.getRecipes()
                .stream()
                .map(RecipeMapper::toDto)
                .collect(Collectors.toList());

        return new RecipeListResponseDto(
                entity.getId(),
                entity.getName(),
                recipes
        );
    }

    public static RecipeList toEntity(RecipeListRequestDto dto, List<Recipe> recipes) {
        if (dto == null) return null;

        RecipeList entity = new RecipeList();
        entity.setName(dto.getName());
        entity.setRecipes(recipes);
        return entity;
    }
}
