package edu.uade.cookingrecipes.dto.request.embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PracticeRequestDto {
    private String description;
    private String instructions;
    private String recommendations;
}
