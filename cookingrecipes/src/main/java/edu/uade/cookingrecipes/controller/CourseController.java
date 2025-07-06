package edu.uade.cookingrecipes.controller;

import edu.uade.cookingrecipes.dto.request.CourseRequestDto;
import edu.uade.cookingrecipes.dto.response.CourseResponseDto;
import edu.uade.cookingrecipes.service.CourseService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @GetMapping("/") //Obtener todos los cursos que no finalizaron
    public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
        List<CourseResponseDto> courses = courseService.getAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/public") //Obtener todos los cursos que no finalizaron
    public ResponseEntity<List<CourseResponseDto>> getAllCoursesPublic() {
        List<CourseResponseDto> courses = courseService.getAllCoursesPublic();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/site/{siteId}") //Obtener todos los cursos de un site
    public ResponseEntity<List<CourseResponseDto>> getAllCoursesBySiteId(@PathVariable Long siteId) {
        return ResponseEntity.ok(courseService.getAllCoursesBySiteId(siteId));
    }

    @PostMapping("/") //Crear un nuevo curso
    public ResponseEntity<CourseResponseDto> createCourse(@RequestBody CourseRequestDto courseDto) {
        CourseResponseDto createdCourse = courseService.createCourse(courseDto);
        if (createdCourse != null) {
            return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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

    @PostMapping("/{courseId}/{userId}/enrollAdmin") //Inscribir a un usuario por admin a un curso
    public ResponseEntity<Void> enrollUserInCourseByAdmin(@PathVariable Long courseId,
                                                           @PathVariable Long userId) {
        boolean isEnrolled = courseService.enrollUserInCourseByAdmin(courseId, userId);
        if (isEnrolled) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{courseId}/unroll/{account}") //Desinscribir al usuario actual a un curso
    public ResponseEntity<Void> unrollUserFromCourse(@PathVariable Long courseId,
                                                     @PathVariable boolean account) {
        boolean isUnrolled = courseService.unrollUserFromCourse(courseId, account);
        if (isUnrolled) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("my-courses/{current}")
    public ResponseEntity<List<CourseResponseDto>>  getMyCourses(@PathVariable Boolean current) {
        List<CourseResponseDto> courses = courseService.getMyCourses(current);
        logger.info("RETURNING MY COURSES: " + courses.toString());
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
}
