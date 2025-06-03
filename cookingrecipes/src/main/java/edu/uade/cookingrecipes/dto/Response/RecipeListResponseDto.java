package edu.uade.cookingrecipes.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeListResponseDto {
    private Long id;
    private String name;
    private List<RecipeResponseDto> recipes;
}
