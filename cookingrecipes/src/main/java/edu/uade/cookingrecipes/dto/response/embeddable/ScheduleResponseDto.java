package edu.uade.cookingrecipes.dto.response.embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponseDto {
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
