package edu.uade.cookingrecipes.mapper;

import edu.uade.cookingrecipes.Entity.Recipe;
import edu.uade.cookingrecipes.dto.Request.RecipeRequestDto;
import edu.uade.cookingrecipes.dto.Response.RecipeResponseDto;

public class RecipeMapper {
    public static RecipeResponseDto toDto(Recipe recipe) {
        if (recipe == null) return null;

        return new RecipeResponseDto(
                recipe.getId(),
                recipe.getUser().getName(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getServings(),
                recipe.getDishType(),
                recipe.getPhotos(),
                recipe.getIngredients(),
                recipe.getSteps()
        );
    }

    public static Recipe toEntity(RecipeRequestDto dto) {
        if (dto == null) return null;

        Recipe recipe = new Recipe();
        recipe.setName(dto.getName());
        recipe.setDescription(dto.getDescription());
        recipe.setServings(dto.getServings());
        recipe.setDishType(dto.getDishType());
        recipe.setPhotos(dto.getPhotos());
        recipe.setIngredients(dto.getIngredients());
        recipe.setSteps(dto.getSteps());
        recipe.setAverageRating(0.0);
        recipe.setApproved(false);
        return recipe;
    }
}
