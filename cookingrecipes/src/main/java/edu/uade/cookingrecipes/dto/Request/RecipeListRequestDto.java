package edu.uade.cookingrecipes.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeListRequestDto {
    String name;
    private List<Long> recipeIds;
}
