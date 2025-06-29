package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.entity.Rating;
import edu.uade.cookingrecipes.entity.Recipe;
import edu.uade.cookingrecipes.dto.request.RatingRequestDto;
import edu.uade.cookingrecipes.dto.response.RatingResponseDto;
import edu.uade.cookingrecipes.dto.response.RecipeResponseDto;
import edu.uade.cookingrecipes.mapper.RatingMapper;
import edu.uade.cookingrecipes.mapper.RecipeMapper;
import edu.uade.cookingrecipes.repository.RatingRepository;
import edu.uade.cookingrecipes.repository.RecipeRepository;
import edu.uade.cookingrecipes.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public RatingResponseDto ratingRecipe(Long recipeId, RatingRequestDto ratingRequestDto) {
        Recipe recipe = recipeRepository.findById(ratingRequestDto.getRecipeId()).orElse(null);
        if (recipe == null) return null;

        Rating rating = RatingMapper.toEntity(ratingRequestDto);
        rating.setRecipe(recipe);
        ratingRepository.save(rating);
        recipeRepository.save(recipe);

        return RatingMapper.toDto(rating);
    }

    @Override
    public RatingResponseDto updateRating(Long ratingId, RatingRequestDto ratingRequestDto) {
        Rating rating = ratingRepository.findById(ratingId).orElse(null);
        if (rating == null) return null;

        rating.setRatingValue(ratingRequestDto.getRatingValue());
        rating.setComment(ratingRequestDto.getComment());

        return RatingMapper.toDto(ratingRepository.save(rating));
    }

    @Override
    public boolean deleteRating(Long ratingId) {
        Rating rating = ratingRepository.findById(ratingId).orElse(null);
        if (rating == null) return false;

        ratingRepository.delete(rating);
        return true;
    }

    @Override
    public boolean approveRating(Long ratingId) {
        Rating rating = ratingRepository.findById(ratingId).orElse(null);
        Long recipeId = ratingRepository.findRecipeIdById(ratingId);
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        if (recipe == null || rating == null) return false;
        recipe.setRatingsCount(recipe.getRatingsCount() + 1);
        double totalRating = recipe.getAverageRating() * (recipe.getRatingsCount() - 1);
        totalRating += rating.getRatingValue();
        recipe.setAverageRating(totalRating / recipe.getRatingsCount());
        recipeRepository.save(recipe);
        rating.setApproved(true);
        ratingRepository.save(rating);

        return true;
    }

    @Override
    public List<RatingResponseDto> getRatingsByRecipeId(Long recipeId) {
        return ratingRepository.findByRecipeId(recipeId)
                .stream()
                .map(RatingMapper::toDto)
                .toList();
    }
}
