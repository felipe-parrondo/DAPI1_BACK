package edu.uade.cookingrecipes.dto.response;

import edu.uade.cookingrecipes.dto.response.embeddable.PracticeResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCourseResponseDto {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String schedule;
    private int duration;
    private int maxParticipants;
    private Integer numClassroom;
    private String siteAddress;
    private double price;
    private double discount;
    private String teacherName;
    private String modality;
    private String objectives;
    private List<String> subjects;
    private List<PracticeResponseDto> practices;
    private List<String> tools;
    private List<String> supplies;
    private List<String> mediaUrl;
    private String assistance;
}
