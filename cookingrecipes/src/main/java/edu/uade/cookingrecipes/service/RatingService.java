package edu.uade.cookingrecipes.service;

import edu.uade.cookingrecipes.dto.request.RatingRequestDto;
import edu.uade.cookingrecipes.dto.response.RatingResponseDto;
import edu.uade.cookingrecipes.dto.response.RecipeResponseDto;

import java.util.List;

public interface RatingService {
    RatingResponseDto ratingRecipe(Long recipeId, RatingRequestDto ratingRequestDto);
    RatingResponseDto updateRating(Long ratingId, RatingRequestDto ratingRequestDto);
    boolean deleteRating(Long ratingId);
    boolean approveRating(Long ratingId, Boolean isApproved);
    List<RatingResponseDto> getRatingsByRecipeId(Long recipeId);
    void rejectRatingsByUserId(Long userId);
}
