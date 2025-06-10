package edu.uade.cookingrecipes.service;

import edu.uade.cookingrecipes.dto.Request.RatingRequestDto;
import edu.uade.cookingrecipes.dto.Response.RatingResponseDto;
import edu.uade.cookingrecipes.dto.Response.RecipeResponseDto;

import java.util.List;

public interface RatingService {
    RecipeResponseDto ratingRecipe(Long recipeId, RatingRequestDto ratingRequestDto);
    RatingResponseDto updateRating(Long ratingId, RatingRequestDto ratingRequestDto);
    boolean deleteRating(Long ratingId);
    boolean approveRating(Long ratingId);
    List<RatingResponseDto> getRatingsByRecipeId(Long recipeId);
}
