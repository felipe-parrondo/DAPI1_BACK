package edu.uade.cookingrecipes.dto.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.uade.cookingrecipes.Entity.Embeddable.IngredientEmbeddable;
import edu.uade.cookingrecipes.Entity.Embeddable.Step;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeRequestDto {
    private String name;
    private String description;
    private Integer servings;
    private String dishType;
    private List<String> photos;
    private List<IngredientEmbeddable> ingredients;
    private List<Step> steps;
}