package edu.uade.cookingrecipes.mapper;

import edu.uade.cookingrecipes.Entity.Course;
import edu.uade.cookingrecipes.dto.Response.CourseResponseDto;

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
                course.getSchedule(),
                course.getDuration(),
                course.getMaxParticipants(),
                course.getSite(),
                course.getPrice(),
                course.getDiscount(),
                course.getTeacherName(),
                course.getModality(),
                course.getObjectives(),
                course.getSubjects(),
                course.getPractices(),
                course.getTools(),
                course.getMediaUrl(),
                course.isActive()
        );
    }
}
