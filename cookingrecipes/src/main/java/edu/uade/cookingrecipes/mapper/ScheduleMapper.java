package edu.uade.cookingrecipes.mapper;

import edu.uade.cookingrecipes.Entity.Embeddable.Schedule;
import edu.uade.cookingrecipes.dto.Request.ScheduleRequestDto;
import edu.uade.cookingrecipes.dto.Response.ScheduleResponseDto;

import java.time.LocalTime;

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
}
