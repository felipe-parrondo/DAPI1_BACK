package edu.uade.cookingrecipes.mapper;

import edu.uade.cookingrecipes.entity.Recipe;
import edu.uade.cookingrecipes.dto.request.RecipeRequestDto;
import edu.uade.cookingrecipes.dto.response.RecipeResponseDto;

public class RecipeMapper {
    public static RecipeResponseDto toDto(Recipe recipe) {
        if (recipe == null) return null;

        return new RecipeResponseDto(
                recipe.getId(),
                recipe.getUser().getUsername(),
                recipe.getUser().getAvatar(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getServings(),
                recipe.getDishType(),
                recipe.getPhotos(),
                recipe.getIngredients(),
                recipe.getSteps(),
                recipe.getUser().getId()
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
