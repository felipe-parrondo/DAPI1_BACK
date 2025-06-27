package edu.uade.cookingrecipes.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uade.cookingrecipes.dto.request.embeddable.PracticeRequestDto;
import edu.uade.cookingrecipes.dto.request.embeddable.ScheduleRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequestDto {
    private String name;
    @JsonProperty("startDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonProperty("endDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String description;
    private ScheduleRequestDto schedule;
    private int duration;
    @JsonProperty("maxParticipants")
    private int maxParticipants;
    @JsonProperty("siteId")
    private Long siteId;
    private double price;
    private double discount;
    @JsonProperty("teacherName")
    private String teacherName;
    private String modality;
    private String objectives;
    private String subjects;
    private List<PracticeRequestDto> practices;
    private String tools;
    @JsonProperty("mediaUrl")
    private List<String> mediaUrl;
}
