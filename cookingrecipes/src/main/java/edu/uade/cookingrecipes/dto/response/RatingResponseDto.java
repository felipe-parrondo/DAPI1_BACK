package edu.uade.cookingrecipes.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingResponseDto {
    private Long id;
    @JsonProperty("ratingValue")
    private Integer ratingValue;
    private String comment;
    private Boolean approved;
    private LocalDateTime ratedAt;
    private String urlAvatar;
    private String username;
    private Long recipeId;
    private Boolean isMyRating;
    private Long userId;
}