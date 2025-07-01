package edu.uade.cookingrecipes.mapper.embeddable;

import edu.uade.cookingrecipes.entity.embeddable.Schedule;
import edu.uade.cookingrecipes.dto.request.embeddable.ScheduleRequestDto;
import edu.uade.cookingrecipes.dto.response.embeddable.ScheduleResponseDto;

public class ScheduleMapper {
    public static Schedule toEntity(ScheduleRequestDto dto) {
        if (dto == null) return null;

        Schedule schedule = new Schedule();
        schedule.setDayOfWeek(dto.getDayOfWeek());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());

        return schedule;
    }

    public static ScheduleResponseDto toDto(Schedule schedule) {
        if (schedule == null) return null;

        ScheduleResponseDto dto = new ScheduleResponseDto();
        dto.setDayOfWeek(schedule.getDayOfWeek());
        dto.setStartTime(schedule.getStartTime());
        dto.setEndTime(schedule.getEndTime());

        return dto;
    }

    public static String toString(Schedule schedule) {
        if (schedule == null) return null;

        return String.format
                ("%s: %s hs -  %s hs", schedule.getDayOfWeek(), schedule.getStartTime(), schedule.getEndTime());
    }
}
