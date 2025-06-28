package edu.uade.cookingrecipes.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientEmbeddable {
    private String name;
    private double quantity;
    private Double scale;
    private String unidad;
}
