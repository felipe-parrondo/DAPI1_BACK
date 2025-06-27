package edu.uade.cookingrecipes.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class IngredientEmbeddable {
    private String name;
    private double quantity;
    private double scale;
}
