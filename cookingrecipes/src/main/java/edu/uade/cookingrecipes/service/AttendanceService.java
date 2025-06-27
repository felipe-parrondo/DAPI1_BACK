package edu.uade.cookingrecipes.service;

import edu.uade.cookingrecipes.dto.response.attendance.CourseAttendanceResponseDto;
import edu.uade.cookingrecipes.dto.response.attendance.SiteAttendanceResponseDto;

import java.util.List;

public interface AttendanceService {
    CourseAttendanceResponseDto registerCourseAttendance(Long userId, Long courseId);
    CourseAttendanceResponseDto getUserAttendanceInCourse(Long userId, Long courseId);
    SiteAttendanceResponseDto registerSiteAttendance(Long userId, Long siteId);
    SiteAttendanceResponseDto getUserAttendanceInSite(Long userId, Long siteId);
    List<CourseAttendanceResponseDto> getAllAttendancesInCourse(Long courseId);
}
