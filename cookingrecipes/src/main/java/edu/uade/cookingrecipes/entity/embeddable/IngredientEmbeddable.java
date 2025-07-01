package edu.uade.cookingrecipes.entity.embeddable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.uade.cookingrecipes.entity.Recipe;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recipe_ingredients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "recipe") // <-- evita el ciclo
public class IngredientEmbeddable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double quantity;
    private String unidad;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    @JsonIgnore
    private Recipe recipe;
}
