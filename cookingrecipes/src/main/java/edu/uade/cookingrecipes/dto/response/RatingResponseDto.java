package edu.uade.cookingrecipes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingResponseDto {
    private Long id;
    private Integer ratingValue;
    private String comment;
    private Boolean approved;
    private LocalDateTime ratedAt;
    private Long recipeId;
}
