package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.dto.response.attendance.SiteAttendanceResponseDto;
import edu.uade.cookingrecipes.entity.CourseAttendance;
import edu.uade.cookingrecipes.dto.response.attendance.CourseAttendanceResponseDto;
import edu.uade.cookingrecipes.entity.SiteAttendance;
import edu.uade.cookingrecipes.mapper.CourseMapper;
import edu.uade.cookingrecipes.mapper.SiteMapper;
import edu.uade.cookingrecipes.mapper.UserMapper;
import edu.uade.cookingrecipes.repository.*;
import edu.uade.cookingrecipes.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private CourseAttendanceRepository courseAttendanceRepository;

    @Autowired
    private SiteAttendanceRepository siteAttendanceRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CourseAttendanceResponseDto registerCourseAttendance(Long userId, Long courseId) {
        CourseAttendance attendance = new CourseAttendance();
        attendance.setUser(userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found with id: " + userId)));
        attendance.setCourse(courseRepository.findById(courseId).orElseThrow(
                () -> new IllegalArgumentException("Course not found with id: " + courseId)));
        attendance.setAttendanceDate(LocalDate.now());
        attendance.setPresent(true);

        CourseAttendance savedAttendance = courseAttendanceRepository.save(attendance);
        return new CourseAttendanceResponseDto(
                savedAttendance.getId(),
                UserMapper.toDto(savedAttendance.getUser()),
                CourseMapper.toDto(savedAttendance.getCourse()),
                savedAttendance.getAttendanceDate(),
                savedAttendance.isPresent()
        );
    }

    @Override
    public CourseAttendanceResponseDto getUserAttendanceInCourse(Long userId, Long courseId) {
        return courseAttendanceRepository.findByUserIdAndCourseId(userId, courseId)
                .stream()
                .findFirst()
                .map(attendance -> new CourseAttendanceResponseDto(
                        attendance.getId(),
                        UserMapper.toDto(attendance.getUser()),
                        CourseMapper.toDto(attendance.getCourse()),
                        attendance.getAttendanceDate(),
                        attendance.isPresent()))
                .orElse(null);
    }

    @Override
    public SiteAttendanceResponseDto registerSiteAttendance(Long userId, Long siteId) {
        SiteAttendance attendance = new SiteAttendance();
        attendance.setUser(userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found with id: " + userId)));
        attendance.setSite(siteRepository.findById(siteId).orElseThrow(
                () -> new IllegalArgumentException("Site not found with id: " + siteId)));
        attendance.setAttendanceDate(LocalDate.now());
        attendance.setPresent(true);

        SiteAttendance savedAttendance = siteAttendanceRepository.save(attendance);
        return new SiteAttendanceResponseDto(
                savedAttendance.getId(),
                UserMapper.toDto(savedAttendance.getUser()),
                SiteMapper.toDto(savedAttendance.getSite()),
                savedAttendance.getAttendanceDate(),
                savedAttendance.isPresent()
        );
    }

    @Override
    public SiteAttendanceResponseDto getUserAttendanceInSite(Long userId, Long siteId) {
        return siteAttendanceRepository.findByUserIdAndSiteId(userId, siteId)
                .stream()
                .findFirst()
                .map(attendance -> new SiteAttendanceResponseDto(
                        attendance.getId(),
                        UserMapper.toDto(attendance.getUser()),
                        SiteMapper.toDto(attendance.getSite()),
                        attendance.getAttendanceDate(),
                        attendance.isPresent()))
                .orElse(null);
    }

    @Override
    public List<CourseAttendanceResponseDto> getAllAttendancesInCourse(Long courseId) {
        return courseAttendanceRepository.findByCourseId(courseId)
                .stream()
                .map(attendance -> new CourseAttendanceResponseDto(
                        attendance.getId(),
                        UserMapper.toDto(attendance.getUser()),
                        CourseMapper.toDto(attendance.getCourse()),
                        attendance.getAttendanceDate(),
                        attendance.isPresent()))
                .toList();
    }
}
