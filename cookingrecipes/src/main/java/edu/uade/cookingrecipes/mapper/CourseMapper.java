package edu.uade.cookingrecipes.mapper;

import edu.uade.cookingrecipes.entity.Course;
import edu.uade.cookingrecipes.dto.request.CourseRequestDto;
import edu.uade.cookingrecipes.dto.response.CourseResponseDto;
import edu.uade.cookingrecipes.entity.Site;
import edu.uade.cookingrecipes.mapper.embeddable.PracticeMapper;
import edu.uade.cookingrecipes.mapper.embeddable.ScheduleMapper;
import edu.uade.cookingrecipes.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;

public class CourseMapper {

    public static CourseResponseDto toDto(Course course, String assistance) {
        if (course == null) {
            return null;
        }

        return new CourseResponseDto(
                course.getId(),
                course.getName(),
                course.getStartDate(),
                course.getEndDate(),
                course.getDescription(),
                ScheduleMapper.toString(course.getSchedule()),
                course.getDuration(),
                course.getMaxParticipants(),
                course.getClassroom().getClassNumber(),
                course.getSite().getId(),
                course.getClassroom().getSite().getAddress(),
                course.getPrice(),
                course.getDiscount(),
                course.getTeacherName(),
                course.getModality(),
                course.getObjectives(),
                course.getSubjects(),
                PracticeMapper.toDto(course.getPracticesList()),
                course.getTools(),
                course.getSupplies(),
                course.getMediaUrl(),
                assistance
        );
    }

    public static Course toEntity(CourseRequestDto courseDto, Site site) {
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
        course.setPracticesList(PracticeMapper.toEntity(courseDto.getPractices()));
        course.setTools(courseDto.getTools());
        course.setMediaUrl(courseDto.getMediaUrl());
        course.setSite(site);
        course.setSupplies(courseDto.getSupplies());

        return course;
    }
}
