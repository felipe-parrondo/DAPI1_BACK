package edu.uade.cookingrecipes.dto.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uade.cookingrecipes.Entity.Embeddable.IngredientEmbeddable;
import edu.uade.cookingrecipes.Entity.Embeddable.Step;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeRequestDto {
    private String name;
    private String description;
    private Integer servings;
    @JsonProperty("dishType")
    private String dishType;
    private List<String> photos;
    private List<IngredientEmbeddable> ingredients;
    private List<Step> steps;
}
