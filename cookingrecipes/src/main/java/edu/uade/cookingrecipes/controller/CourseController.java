package edu.uade.cookingrecipes.controller;

import edu.uade.cookingrecipes.dto.Request.CourseRequestDto;
import edu.uade.cookingrecipes.dto.Response.AttendanceResponseDto;
import edu.uade.cookingrecipes.dto.Response.CourseResponseDto;
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
    public ResponseEntity<AttendanceResponseDto> registerAttendance(@PathVariable Long userId,
                                                                    @PathVariable Long courseId) {
        AttendanceResponseDto attendance = attendanceService.registerAttendance(userId, courseId);
        if (attendance != null) {
            return new ResponseEntity<>(attendance, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{userId}/{courseId}/attendance") //Obtener asistencia de un usuario en un curso
    public ResponseEntity<AttendanceResponseDto> getUserAttendanceInCourse(@PathVariable Long courseId,
                                                                           @PathVariable Long userId) {
        AttendanceResponseDto attendance = attendanceService.getUserAttendanceInCourse(courseId, userId);
        if (attendance != null) {
            return new ResponseEntity<>(attendance, HttpStatus.OK);
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

    @PostMapping("/{courseId}/{userId}/enroll") //Inscribir un usuario a un curso
    public ResponseEntity<Void> enrollUserInCourse(@PathVariable Long courseId, @PathVariable Long userId) {
        boolean isEnrolled = courseService.enrollUserInCourse(courseId, userId);
        if (isEnrolled) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{courseId}/{userId}/unroll") //Desinscribir un usuario de un curso
    public ResponseEntity<Void> unrollUserFromCourse(@PathVariable Long courseId, @PathVariable Long userId) {
        boolean isUnrolled = courseService.unrollUserFromCourse(courseId, userId);
        if (isUnrolled) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
