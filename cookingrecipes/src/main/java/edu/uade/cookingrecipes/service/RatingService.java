package edu.uade.cookingrecipes.service;

import edu.uade.cookingrecipes.dto.request.RatingRequestDto;
import edu.uade.cookingrecipes.dto.request.UpdateRatingRequestDto;
import edu.uade.cookingrecipes.dto.response.RatingResponseDto;
import edu.uade.cookingrecipes.dto.response.RecipeResponseDto;
import edu.uade.cookingrecipes.entity.Rating;

import java.util.List;

public interface RatingService {
    RatingResponseDto ratingRecipe(Long recipeId, RatingRequestDto ratingRequestDto);
    boolean updateRating(UpdateRatingRequestDto updateRatingRequestDto);
    boolean deleteRating(Long ratingId);
    boolean approveRating(Long ratingId, Boolean isApproved);
    List<RatingResponseDto> getRatingsByStatus(Boolean status);
    List<RatingResponseDto> getRatings();
    List<RatingResponseDto> getRatingsByRecipeId(Long recipeId);
    List<RatingResponseDto> getRatingsByRecipeIdPublic(Long recipeId);
    void rejectRatingsByUserId(Long userId);
    RatingResponseDto getRatingById(Long ratingId);
    List<RatingResponseDto> getRatingsByUser();
}
