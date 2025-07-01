package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.entity.Rating;
import edu.uade.cookingrecipes.entity.Recipe;
import edu.uade.cookingrecipes.dto.request.RatingRequestDto;
import edu.uade.cookingrecipes.dto.response.RatingResponseDto;
import edu.uade.cookingrecipes.dto.response.RecipeResponseDto;
import edu.uade.cookingrecipes.mapper.RatingMapper;
import edu.uade.cookingrecipes.mapper.RecipeMapper;
import edu.uade.cookingrecipes.model.AuthenticationModel;
import edu.uade.cookingrecipes.model.UserModel;
import edu.uade.cookingrecipes.repository.AuthenticationRepository;
import edu.uade.cookingrecipes.repository.RatingRepository;
import edu.uade.cookingrecipes.repository.RecipeRepository;
import edu.uade.cookingrecipes.service.RatingService;
import edu.uade.cookingrecipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private UserService userService;

    @Override
    public RatingResponseDto ratingRecipe(Long recipeId, RatingRequestDto ratingRequestDto) {
        Recipe recipe = recipeRepository.findById(ratingRequestDto.getRecipeId()).orElse(null);
        if (recipe == null)
            throw new IllegalArgumentException("Recipe not found with ID: " + ratingRequestDto.getRecipeId());
        UserModel user = userService.getUser();
        Rating rating = RatingMapper.toEntity(ratingRequestDto);
        rating.setRecipe(recipe);
        rating.setUser(user);
        ratingRepository.save(rating);
        recipeRepository.save(recipe);

        return RatingMapper.toDto(rating, user);
    }

    @Override
    public RatingResponseDto updateRating(Long ratingId, RatingRequestDto ratingRequestDto) {
        Rating rating = ratingRepository.findById(ratingId).orElse(null);
        if (rating == null) return null;

        rating.setRatingValue(ratingRequestDto.getRatingValue());
        rating.setComment(ratingRequestDto.getComment());

        return RatingMapper.toDto(ratingRepository.save(rating), userService.getUser());
    }

    @Override
    public boolean deleteRating(Long ratingId) {
        Rating rating = ratingRepository.findById(ratingId).orElse(null);
        if (rating == null) return false;

        ratingRepository.delete(rating);
        return true;
    }

    @Override
    public boolean approveRating(Long ratingId, Boolean isApproved) {
        Rating rating = ratingRepository.findById(ratingId).orElse(null);
        Long recipeId = ratingRepository.findRecipeIdById(ratingId);
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        if (recipe == null || rating == null) return false;
        recipe.setRatingsCount(recipe.getRatingsCount() + 1);
        double totalRating = recipe.getAverageRating() * (recipe.getRatingsCount() - 1);
        totalRating += rating.getRatingValue();
        recipe.setAverageRating(totalRating / recipe.getRatingsCount());
        recipeRepository.save(recipe);
        rating.setApproved(isApproved);
        ratingRepository.save(rating);

        return true;
    }
    @Override
    public List<RatingResponseDto> getRatingsByStatus(Boolean status) {
        return ratingRepository.findByApproved(status).stream()
                .map(r -> RatingMapper.toDto(r, userService.getUser()))
                .toList();
    }

    @Override
    public List<RatingResponseDto> getRatings() {
        return ratingRepository.findAll().stream()
                .map(r -> RatingMapper.toDto(r, userService.getUser()))
                .toList();
    }

    @Override // Traer todos los comentarios de una receta, y si la receta la subiste vos, traer tus comentarios esten aprovados o no
    public List<RatingResponseDto> getRatingsByRecipeId(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        if (recipe == null) return List.of();

        UserModel user = getUser();

        List<Rating> ratings;
        if (user.getId().equals(recipe.getUser().getId())) {
            ratings = ratingRepository.findByRecipeIdAndUserId(recipeId, user.getId());
        } else {
            ratings = ratingRepository.findByRecipeIdAndApprovedTrue(recipeId);
        }

        return ratings.stream()
                .map(r -> RatingMapper.toDto(r, user))
                .toList();
    }

    @Override
    public void rejectRatingsByUserId(Long userId) {
        List<Rating> ratingList = ratingRepository.findByUser_Id(userId);
        ratingList.forEach(r -> {
            r.setApproved(false);
            ratingRepository.save(r);
        });
    }

    private UserModel getUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserModel user = authenticationRepository.findByEmail(email)
                .map(AuthenticationModel::getUser)
                .orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("User not found: " + email);
        }
        return user;
    }
}
