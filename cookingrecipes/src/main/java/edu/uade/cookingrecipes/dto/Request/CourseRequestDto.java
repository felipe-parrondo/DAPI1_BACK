package edu.uade.cookingrecipes.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequestDto {
    private String name;
    private String startDate;
    private String endDate;
    private String description;
    private ScheduleRequestDto schedule;
    private int duration;
    private int maxParticipants;
    private Long siteId;
    private double price;
    private double discount;
    private String teacherName;
    private String modality;
    private String objectives;
    private String subjects;
    private String practices;
    private String tools;
    private List<String> mediaUrl;
}
