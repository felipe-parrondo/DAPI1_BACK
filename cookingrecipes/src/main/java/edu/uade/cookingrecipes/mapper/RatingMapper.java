package edu.uade.cookingrecipes.mapper;

import edu.uade.cookingrecipes.entity.Rating;
import edu.uade.cookingrecipes.dto.request.RatingRequestDto;
import edu.uade.cookingrecipes.dto.response.RatingResponseDto;
import edu.uade.cookingrecipes.model.UserModel;

import java.time.LocalDateTime;
import java.util.List;

public class RatingMapper {

    public static RatingResponseDto toDto(Rating rating, UserModel user){
        if (rating == null) return null;

        return new RatingResponseDto(
                rating.getId(),
                rating.getRatingValue(),
                rating.getComment(),
                rating.getApproved(),
                rating.getRatedAt(),
                user.getAvatar(),
                user.getUsername(),
                rating.getRecipe().getId(),
                false,
                rating.getUser() != null ? rating.getUser().getId() : null
        );
    }

    public static Rating toEntity(RatingRequestDto dto) {
        if (dto == null) return null;

        Rating rating = new Rating();
        rating.setRatingValue(dto.getRatingValue());
        rating.setComment(dto.getComment());
        rating.setApproved(null);
        rating.setRatedAt(LocalDateTime.now());
        return rating;
    }
}
