package edu.uade.cookingrecipes.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRequestDto {

    @JsonProperty("courseId")
    private Long courseId;

    @JsonProperty("presentSite")
    private boolean presentSite;

    @JsonProperty("presentClassroom")
    private boolean presentClassroom;

    @JsonProperty("siteId")
    private Long siteId;

    @JsonProperty("classroomId")
    private Long classroomId;
}