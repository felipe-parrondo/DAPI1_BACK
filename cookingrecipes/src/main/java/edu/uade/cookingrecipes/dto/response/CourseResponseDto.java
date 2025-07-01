package edu.uade.cookingrecipes.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uade.cookingrecipes.dto.response.embeddable.PracticeResponseDto;
import edu.uade.cookingrecipes.dto.response.embeddable.ScheduleResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDto {
    private Long id;
    private String name;
    @JsonProperty("startDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonProperty("endDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String description;
    private String schedule;
    private int duration;
    @JsonProperty("maxParticipants")
    private int maxParticipants;
    @JsonProperty("numClassroom")
    private Integer numClassroom;
    @JsonProperty("siteId")
    private Long siteId;
    @JsonProperty("siteAddress")
    private String siteAddress;
    private double price;
    private double discount;
    @JsonProperty("teacherName")
    private String teacherName;
    private String modality;
    private String objectives;
    private List<String> subjects;
    private List<PracticeResponseDto> practices;
    private List<String> tools;
    private List<String> supplies;
    @JsonProperty("mediaUrl")
    private List<String> mediaUrl;
    @JsonProperty("attendancePercentage")
    private String attendancePercentage;
}

