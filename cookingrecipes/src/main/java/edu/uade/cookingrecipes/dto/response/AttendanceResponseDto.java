package edu.uade.cookingrecipes.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceResponseDto {

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("courseId")
    private Long courseId;

    @JsonProperty("presentSite")
    private boolean presentSite;

    @JsonProperty("presentClassroom")
    private boolean presentClassroom;

    @JsonProperty("presenceDateTime")
    private String presenceDateTime;
}
