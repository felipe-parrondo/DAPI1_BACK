package edu.uade.cookingrecipes.Entity.Embeddable;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IngredientEmbeddable {
    private String name;
    private double quantity;
    private double Scale;
}
