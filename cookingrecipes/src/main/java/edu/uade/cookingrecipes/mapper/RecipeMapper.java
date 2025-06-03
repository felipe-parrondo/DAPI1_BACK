package edu.uade.cookingrecipes.mapper;

import edu.uade.cookingrecipes.Entity.Recipe;
import edu.uade.cookingrecipes.dto.Response.RecipeResponseDto;

public interface RecipeMapper {

    public static RecipeResponseDto toDto(Recipe recipe) {
        if (recipe == null) return null;

        return new RecipeResponseDto(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getServings(),
                recipe.getDishType(),
                recipe.getPhotos(),
                recipe.getIngredients(),
                recipe.getSteps()
        );
    }

}
