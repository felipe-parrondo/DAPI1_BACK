package edu.uade.cookingrecipes.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateRatingRequestDto {
    private Long id;
    @JsonProperty("recipeId")
    private Long recipeId;

    @JsonProperty("ratingValue")
    private Integer ratingValue;

    @JsonProperty("comment")
    private String comment;
}
