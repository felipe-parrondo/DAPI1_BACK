package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.Entity.Attendance;
import edu.uade.cookingrecipes.dto.Response.AttendanceResponseDto;
import edu.uade.cookingrecipes.repository.AttendanceRepository;
import edu.uade.cookingrecipes.repository.CourseRepository;
import edu.uade.cookingrecipes.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public AttendanceResponseDto registerAttendance(Long userId, Long courseId) {
        Attendance attendance = new Attendance();
        attendance.setUserId(userId);
        attendance.setCourse(courseRepository.findById(courseId).orElseThrow(
                () -> new IllegalArgumentException("Course not found with id: " + courseId)));
        attendance.setAttendanceDate(LocalDate.now());
        attendance.setPresent(true); // Assuming the user is present when registering

        Attendance savedAttendance = attendanceRepository.save(attendance);
        return new AttendanceResponseDto(
                savedAttendance.getId(),
                savedAttendance.getUser(),
                savedAttendance.getCourse(),
                savedAttendance.getAttendanceDate(),
                savedAttendance.isPresent()
        );
    }

    @Override
    public AttendanceResponseDto getUserAttendanceInCourse(Long userId, Long courseId) {
        return attendanceRepository.findByUserIdAndCourseId(userId, courseId)
                .stream()
                .findFirst()
                .map(attendance -> new AttendanceResponseDto(
                        attendance.getId(),
                        attendance.getUser(),
                        attendance.getCourse(),
                        attendance.getAttendanceDate(),
                        attendance.isPresent()))
                .orElse(null);
    }
}
