package edu.uade.cookingrecipes.mapper;

import edu.uade.cookingrecipes.Entity.Embeddable.Schedule;
import edu.uade.cookingrecipes.dto.Request.ScheduleRequestDto;

public class ScheduleMapper {
    public static Schedule mapScheduleDto(ScheduleRequestDto dto) {
        if (dto == null) return null;

        return new Schedule(
                dto.getDayOfWeek(),
                dto.getStartTime(),
                dto.getEndTime()
        );
    }
}
