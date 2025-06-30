package edu.uade.cookingrecipes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientResponseDto {
    private String name;
    private double quantity;
    private String unidad;
}
