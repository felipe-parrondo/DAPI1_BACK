package edu.uade.cookingrecipes.dto.response.attendance;

import edu.uade.cookingrecipes.dto.response.CourseResponseDto;
import edu.uade.cookingrecipes.dto.response.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseAttendanceResponseDto {
    private Long id;
    private UserResponseDto user;
    private CourseResponseDto course;
    private LocalDate attendanceDate;
    private boolean present;
}
