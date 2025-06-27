package edu.uade.cookingrecipes.service;

import edu.uade.cookingrecipes.dto.request.RatingRequestDto;
import edu.uade.cookingrecipes.dto.response.RatingResponseDto;
import edu.uade.cookingrecipes.dto.response.RecipeResponseDto;

import java.util.List;

public interface RatingService {
    RecipeResponseDto ratingRecipe(Long recipeId, RatingRequestDto ratingRequestDto);
    RatingResponseDto updateRating(Long ratingId, RatingRequestDto ratingRequestDto);
    boolean deleteRating(Long ratingId);
    boolean approveRating(Long ratingId);
    List<RatingResponseDto> getRatingsByRecipeId(Long recipeId);
}
