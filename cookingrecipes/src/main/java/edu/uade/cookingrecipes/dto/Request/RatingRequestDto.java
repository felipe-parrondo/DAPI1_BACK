package edu.uade.cookingrecipes.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingRequestDto {
    private Long recipeId;
    private Integer ratingValue;
    private String comment;
}
