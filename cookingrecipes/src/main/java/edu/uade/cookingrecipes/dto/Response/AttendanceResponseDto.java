package edu.uade.cookingrecipes.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponseDto {
    private Long id;
    private UserResponseDto user;
    private CourseResponseDto course;
    private LocalDate attendanceDate;
    private boolean present;
}
