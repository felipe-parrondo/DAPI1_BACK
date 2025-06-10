package edu.uade.cookingrecipes.mapper;

import edu.uade.cookingrecipes.Entity.Rating;
import edu.uade.cookingrecipes.dto.Request.RatingRequestDto;
import edu.uade.cookingrecipes.dto.Response.RatingResponseDto;

import java.time.LocalDateTime;

public class RatingMapper {

    public static RatingResponseDto toDto(Rating rating){
        if (rating == null) return null;

        return new RatingResponseDto(
                rating.getId(),
                rating.getRatingValue(),
                rating.getComment(),
                rating.getApproved(),
                rating.getRatedAt(),
                rating.getRecipe().getId()
        );
    }

    public static Rating toEntity(RatingRequestDto dto) {
        if (dto == null) return null;

        Rating rating = new Rating();
        rating.setRatingValue(dto.getRatingValue());
        rating.setComment(dto.getComment());
        rating.setApproved(false);
        rating.setRatedAt(LocalDateTime.now());
        return rating;
    }
}
