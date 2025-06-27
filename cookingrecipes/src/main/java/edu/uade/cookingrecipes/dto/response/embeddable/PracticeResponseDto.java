package edu.uade.cookingrecipes.dto.response.embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PracticeResponseDto {
    private String description;
    private String instructions;
    private String recommendations;
}
