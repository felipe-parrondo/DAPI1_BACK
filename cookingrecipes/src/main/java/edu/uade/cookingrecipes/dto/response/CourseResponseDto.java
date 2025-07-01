package edu.uade.cookingrecipes.dto.response;

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
    @JsonProperty("start_date")
    private LocalDate startDate;
    @JsonProperty("end_date")
    private LocalDate endDate;
    private String description;
    private String schedule;
    private int duration;
    @JsonProperty("max_participants")
    private int maxParticipants;
    @JsonProperty("num_classroom")
    private Integer numClassroom;
    @JsonProperty("site_id")
    private Long siteId;
    @JsonProperty("site_address")
    private String siteAddress;
    private double price;
    private double discount;
    @JsonProperty("teacher_name")
    private String teacherName;
    private String modality;
    private String objectives;
    private List<String> subjects;
    private List<PracticeResponseDto> practices;
    private List<String> tools;
    private List<String> supplies;
    @JsonProperty("media_url")
    private List<String> mediaUrl;
    @JsonProperty("attendance_percentage")
    private String attendancePercentage;
}

