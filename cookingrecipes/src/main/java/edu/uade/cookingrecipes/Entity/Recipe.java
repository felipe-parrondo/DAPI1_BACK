package edu.uade.cookingrecipes.Entity;

import edu.uade.cookingrecipes.Entity.Embeddable.IngredientEmbeddable;
import edu.uade.cookingrecipes.Entity.Embeddable.Step;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description", length = 2000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    @Column(name = "servings_count", nullable = false)
    private int servings;

    @Column(name = "dish_type", nullable = false)
    private String dishType;

    @ElementCollection
    @CollectionTable(name = "recipe_photos", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "photo_url")
    private List<String> photos;

    @ElementCollection
    @CollectionTable(name = "recipe_ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "ingredient", nullable = false)
    private List<IngredientEmbeddable> ingredients;

    @ElementCollection
    @CollectionTable(name = "recipe_steps", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "step", nullable = false)
    private List<Step> steps;

    @Column(name = "average_rating", nullable = false)
    private double averageRating;

    @Column(name = "ratings_count", nullable = false)
    private int ratingsCount;

    @Column(name = "approved", nullable = false)
    private Boolean approved;
}
