package edu.uade.cookingrecipes.dto.response.attendance;

import edu.uade.cookingrecipes.dto.response.SiteResponseDto;
import edu.uade.cookingrecipes.dto.response.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteAttendanceResponseDto {
    private Long id;
    private UserResponseDto user;
    private SiteResponseDto site;
    private LocalDate attendanceDate;
    private boolean present;
}
