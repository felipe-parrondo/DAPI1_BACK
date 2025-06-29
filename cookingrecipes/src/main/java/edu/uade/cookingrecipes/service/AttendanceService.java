package edu.uade.cookingrecipes.service;

import edu.uade.cookingrecipes.dto.request.AttendanceRequestDto;
import edu.uade.cookingrecipes.dto.response.AttendanceResponseDto;

import java.util.List;

public interface AttendanceService {
    AttendanceResponseDto registerAttendance(AttendanceRequestDto attendanceRequestDto);
}
