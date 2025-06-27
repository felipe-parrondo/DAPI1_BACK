package edu.uade.cookingrecipes.Entity.Embeddable;

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
    private Double scale;
    private String unidad;
}
