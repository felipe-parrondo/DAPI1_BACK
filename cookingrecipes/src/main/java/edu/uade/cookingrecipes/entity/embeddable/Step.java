package edu.uade.cookingrecipes.entity.embeddable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.uade.cookingrecipes.entity.Recipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
@Entity
@Table(name = "recipe_steps")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"recipe"})
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ElementCollection
    @CollectionTable(name = "step_media", joinColumns = @JoinColumn(name = "step_id"))
    private List<Media> media;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}

