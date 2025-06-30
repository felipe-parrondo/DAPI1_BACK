package edu.uade.cookingrecipes.dto.response;

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
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private ScheduleResponseDto schedule;
    private int duration;
    private int maxParticipants;
    private ClassroomResponseDto classroom;
    private double price;
    private double discount;
    private String teacherName;
    private String modality;
    private String objectives;
    private String subjects;
    private List<PracticeResponseDto> practices;
    private String tools;
    private List<String> mediaUrl;
    private List<Long> studentIds;
    private boolean isActive;
}