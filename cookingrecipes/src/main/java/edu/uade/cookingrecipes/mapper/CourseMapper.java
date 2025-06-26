package edu.uade.cookingrecipes.mapper;

import edu.uade.cookingrecipes.Entity.Course;
import edu.uade.cookingrecipes.dto.Request.CourseRequestDto;
import edu.uade.cookingrecipes.dto.Response.CourseResponseDto;

import java.time.LocalDate;

public class CourseMapper {
    public static CourseResponseDto toDto(Course course) {
        if (course == null) {
            return null;
        }

        return new CourseResponseDto(
                course.getId(),
                course.getName(),
                course.getStartDate(),
                course.getEndDate(),
                course.getDescription(),
                ScheduleMapper.toDto(course.getSchedule()),
                course.getDuration(),
                course.getMaxParticipants(),
                SiteMapper.toDto(course.getSite()),
                course.getPrice(),
                course.getDiscount(),
                course.getTeacherName(),
                course.getModality(),
                course.getObjectives(),
                course.getSubjects(),
                course.getPractices(),
                course.getTools(),
                course.getMediaUrl(),
                course.getStudents(),
                course.isActive()
        );
    }

    public static Course toEntity(CourseRequestDto courseDto) {
        if (courseDto == null) {
            return null;
        }

        Course course = new Course();
        course.setName(courseDto.getName());
        course.setStartDate(courseDto.getStartDate());
        course.setEndDate(courseDto.getEndDate());
        course.setDescription(courseDto.getDescription());
        course.setSchedule(ScheduleMapper.toEntity(courseDto.getSchedule()));
        course.setDuration(courseDto.getDuration());
        course.setMaxParticipants(courseDto.getMaxParticipants());
        course.setPrice(courseDto.getPrice());
        course.setDiscount(courseDto.getDiscount());
        course.setTeacherName(courseDto.getTeacherName());
        course.setModality(courseDto.getModality());
        course.setObjectives(courseDto.getObjectives());
        course.setSubjects(courseDto.getSubjects());
        course.setPractices(courseDto.getPractices());
        course.setTools(courseDto.getTools());
        course.setMediaUrl(courseDto.getMediaUrl());

        return course;
    }
}
