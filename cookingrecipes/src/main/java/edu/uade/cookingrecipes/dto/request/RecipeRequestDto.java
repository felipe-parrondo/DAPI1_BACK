package edu.uade.cookingrecipes.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uade.cookingrecipes.entity.embeddable.IngredientEmbeddable;
import edu.uade.cookingrecipes.entity.embeddable.Step;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeRequestDto {
    private Long id;
    private String name;
    private String description;
    private Integer servings;
    @JsonProperty("dish_type")
    private String dishType;
    private List<String> photos;
    private List<IngredientEmbeddable> ingredients;
    private List<Step> steps;
}