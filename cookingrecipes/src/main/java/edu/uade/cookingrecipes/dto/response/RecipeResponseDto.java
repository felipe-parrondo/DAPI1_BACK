package edu.uade.cookingrecipes.dto.response;

import edu.uade.cookingrecipes.entity.embeddable.IngredientEmbeddable;
import edu.uade.cookingrecipes.entity.embeddable.Step;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeResponseDto {
    private Long id;
    private String username;
    private String avatar;
    private String name;
    private String description;
    private Integer servings;
    private String dishType;
    private List<String> photos;
    private List<IngredientEmbeddable> ingredients;
    private List<Step> steps;
    private Long userId;
}
