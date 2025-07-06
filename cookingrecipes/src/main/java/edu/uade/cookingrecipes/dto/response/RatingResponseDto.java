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

    @JsonProperty("ratedAt")
    private LocalDateTime ratedAt;

    @JsonProperty("urlAvatar")
    private String urlAvatar;

    private String username;

    @JsonProperty("recipeId")
    private Long recipeId;

    @JsonProperty("isMyRating")
    private Boolean isMyRating;

    @JsonProperty("userId")
    private Long userId;
}