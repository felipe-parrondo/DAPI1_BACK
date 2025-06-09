package edu.uade.cookingrecipes.dto.Response;

import edu.uade.cookingrecipes.Entity.Course;
import edu.uade.cookingrecipes.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponseDto {
    private Long id;
    private User user;
    private Course course;
    private LocalDate attendanceDate;
    private boolean present;
}
