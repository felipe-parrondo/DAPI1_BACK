package edu.uade.cookingrecipes.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRequestDto {
    private Long userId;
    private Long courseId;
}
