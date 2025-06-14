package edu.uade.cookingrecipes.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDto {
    private String dayOfWeek;
    private String startTime;
    private String endTime;
}
