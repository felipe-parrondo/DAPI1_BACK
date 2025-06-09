package edu.uade.cookingrecipes.dto.Response;

import edu.uade.cookingrecipes.Entity.Embeddable.Schedule;
import edu.uade.cookingrecipes.Entity.Site;
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
    private Schedule schedule;
    private int duration;
    private int maxParticipants;
    private Site site;
    private double price;
    private double discount;
    private String teacherName;
    private String modality;
    private String objectives;
    private String subjects;
    private String practices;
    private String tools;
    private List<String> mediaUrl;
    private boolean isActive;
}
