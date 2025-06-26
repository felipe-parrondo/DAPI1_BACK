package edu.uade.cookingrecipes.service;

import edu.uade.cookingrecipes.dto.Request.CourseRequestDto;
import edu.uade.cookingrecipes.dto.Response.CourseResponseDto;

import java.util.List;

public interface CourseService {
    CourseResponseDto createCourse(CourseRequestDto courseDto);
    List<CourseResponseDto> getAllCourses();
    CourseResponseDto getCourseById(Long courseId);
    boolean deleteCourse(Long courseId);
    boolean enrollUserInCourse(Long userId, Long courseId);
    boolean unrollUserFromCourse(Long courseId, Long userId);
}
