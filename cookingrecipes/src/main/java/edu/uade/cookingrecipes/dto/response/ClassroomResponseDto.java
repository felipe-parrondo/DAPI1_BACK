package edu.uade.cookingrecipes.dto.response;

import edu.uade.cookingrecipes.entity.Site;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomResponseDto {
    private Long id;
    private Integer number;
    private Site site;
}
