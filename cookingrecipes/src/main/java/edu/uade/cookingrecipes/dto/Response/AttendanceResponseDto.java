package edu.uade.cookingrecipes.dto.Response;

import edu.uade.cookingrecipes.Entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponseDto {
    private Long id;
    private Long userId;
    private Course course;
    private LocalDate attendanceDate;
    private boolean present;
}
