package edu.uade.cookingrecipes.dto.request.attendance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseAttendanceRequestDto {
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("courseId")
    private Long courseId;
}
