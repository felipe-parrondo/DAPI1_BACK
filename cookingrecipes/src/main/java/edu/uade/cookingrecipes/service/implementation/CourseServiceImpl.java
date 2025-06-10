package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.Entity.Course;
import edu.uade.cookingrecipes.dto.Response.CourseResponseDto;
import edu.uade.cookingrecipes.mapper.CourseMapper;
import edu.uade.cookingrecipes.repository.CourseRepository;
import edu.uade.cookingrecipes.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<CourseResponseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(CourseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CourseResponseDto getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) return null;
        return CourseMapper.toDto(course);
    }

    @Override
    public boolean deleteCourse(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            return false;
        }
        courseRepository.deleteById(courseId);
        return true;
    }

    @Override
    public boolean enrollUserInCourse(Long userId, Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null || !course.isActive()) {
            return false;
        }
        // Assuming there's a method to enroll a user in a course
        // This part would typically involve more logic, such as checking if the user is already enrolled
        // and if the course has available spots.
        // For now, we will just return true to indicate success.
        return true; // Placeholder for actual enrollment logic
    }

    @Override
    public boolean unrollUserFromCourse(Long courseId, Long userId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null || !course.isActive()) {
            return false;
        }
        // Assuming there's a method to unroll a user from a course
        // This part would typically involve more logic, such as checking if the user is enrolled in the course.
        // For now, we will just return true to indicate success.
        return true; // Placeholder for actual unrolling logic
    }
}
