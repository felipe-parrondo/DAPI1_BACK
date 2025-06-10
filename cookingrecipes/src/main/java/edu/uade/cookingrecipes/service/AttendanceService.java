package edu.uade.cookingrecipes.service;

import edu.uade.cookingrecipes.dto.Response.AttendanceResponseDto;

import java.util.List;

public interface AttendanceService {
    AttendanceResponseDto registerAttendance(Long userId, Long courseId);
    AttendanceResponseDto getUserAttendanceInCourse(Long userId, Long courseId);
}
