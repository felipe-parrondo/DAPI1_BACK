package edu.uade.cookingrecipes.service;

import edu.uade.cookingrecipes.dto.request.CourseRequestDto;
import edu.uade.cookingrecipes.dto.response.CourseResponseDto;

import java.util.List;

public interface CourseService {
    CourseResponseDto createCourse(CourseRequestDto courseDto);
    List<CourseResponseDto> getAllCourses();
    List<CourseResponseDto> getAllCoursesBySiteId(Long siteId);
    CourseResponseDto getCourseById(Long courseId);
    String getUserAttendanceForCourse(Long courseId);
    boolean deleteCourse(Long courseId);
    boolean enrollUserInCourse(Long courseId);
    boolean enrollUserInCourseByAdmin(Long courseId, Long userId);
    boolean unrollUserFromCourse(Long courseId, boolean AccountBalanceRefund);
}
