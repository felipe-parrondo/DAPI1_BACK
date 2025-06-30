package edu.uade.cookingrecipes.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListRequestDto {
    String name;
    @JsonProperty("recipeIds")
    private List<Long> recipeIds;
}
