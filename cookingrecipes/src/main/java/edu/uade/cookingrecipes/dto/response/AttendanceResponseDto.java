package edu.uade.cookingrecipes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceResponseDto {
    private Long userId;
    private Long courseId;
    private boolean presentSite;
    private boolean presentClassroom;
    private String presenceDateTime;
}
