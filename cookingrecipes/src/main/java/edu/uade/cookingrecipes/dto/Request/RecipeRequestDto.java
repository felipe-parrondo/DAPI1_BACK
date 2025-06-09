package edu.uade.cookingrecipes.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRequestDto {
    private String name;
    private String description;
    private Integer servings;
    private String dishType;
    private List<String> photos;
    private List<String> ingredients;
    private List<String> steps;
}
