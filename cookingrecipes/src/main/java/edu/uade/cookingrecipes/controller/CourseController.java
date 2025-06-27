package edu.uade.cookingrecipes.controller;

import edu.uade.cookingrecipes.dto.request.CourseRequestDto;
import edu.uade.cookingrecipes.dto.response.attendance.CourseAttendanceResponseDto;
import edu.uade.cookingrecipes.dto.response.CourseResponseDto;
import edu.uade.cookingrecipes.service.AttendanceService;
import edu.uade.cookingrecipes.service.CourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Course Operations")
@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/") //Obtener todos los cursos
    public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
        List<CourseResponseDto> courses = courseService.getAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @PostMapping("/") //Crear un nuevo curso
    public ResponseEntity<CourseResponseDto> createCourse(@RequestBody CourseRequestDto courseDto) {
        CourseResponseDto createdCourse = courseService.createCourse(courseDto);
        if (createdCourse != null) {
            return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{userId}/{courseId}/register_attendance") //Registrar asistencia de un usuario en un curso
    public ResponseEntity<CourseAttendanceResponseDto> registerAttendance(@PathVariable Long userId,
                                                                          @PathVariable Long courseId) {
        CourseAttendanceResponseDto attendance = attendanceService.registerCourseAttendance(userId, courseId);
        if (attendance != null) {
            return new ResponseEntity<>(attendance, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{userId}/{courseId}/attendance") //Obtener asistencia de un usuario en un curso
    public ResponseEntity<CourseAttendanceResponseDto> getUserAttendanceInCourse(@PathVariable Long userId,
                                                                                 @PathVariable Long courseId) {
        CourseAttendanceResponseDto attendance = attendanceService.getUserAttendanceInCourse(userId, courseId);
        if (attendance != null) {
            return new ResponseEntity<>(attendance, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{courseId}/attendance") //Obtener asistencia de todos los usuarios en un curso
    public ResponseEntity<List<CourseAttendanceResponseDto>> getAllAttendancesInCourse(@PathVariable Long courseId) {
        List<CourseAttendanceResponseDto> attendances = attendanceService.getAllAttendancesInCourse(courseId);
        if (attendances != null && !attendances.isEmpty()) {
            return new ResponseEntity<>(attendances, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{courseId}") //Obtener un curso
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable Long courseId) {
        CourseResponseDto courseDetails = courseService.getCourseById(courseId);
        if (courseDetails != null) {
            return new ResponseEntity<>(courseDetails, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{courseId}") //Eliminar un curso
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        boolean isDeleted = courseService.deleteCourse(courseId);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{courseId}/enroll") //Inscribir al usuario actual a un curso
    public ResponseEntity<Void> enrollUserInCourse(@PathVariable Long courseId) {
        boolean isEnrolled = courseService.enrollUserInCourse(courseId);
        if (isEnrolled) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{courseId}/unroll") //Desinscribir al usuario actual a un curso
    public ResponseEntity<Void> unrollUserFromCourse(@PathVariable Long courseId) {
        boolean isUnrolled = courseService.unrollUserFromCourse(courseId);
        if (isUnrolled) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
