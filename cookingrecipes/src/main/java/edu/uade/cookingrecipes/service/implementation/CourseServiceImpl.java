package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.Entity.Course;
import edu.uade.cookingrecipes.Entity.Site;
import edu.uade.cookingrecipes.dto.Request.CourseRequestDto;
import edu.uade.cookingrecipes.dto.Response.CourseResponseDto;
import edu.uade.cookingrecipes.mapper.CourseMapper;
import edu.uade.cookingrecipes.repository.CourseRepository;
import edu.uade.cookingrecipes.repository.SiteRepository;
import edu.uade.cookingrecipes.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SiteRepository siteRepository;

    @Override
    public CourseResponseDto createCourse(CourseRequestDto courseDto) {
        Course course = CourseMapper.toEntity(courseDto);
        System.out.println(courseDto);
        Site site = siteRepository.findById(courseDto.getSiteId())
                .orElseThrow(() -> new IllegalArgumentException("Site not found with id: " + courseDto.getSiteId()));
        course.setSite(site);
        course.setActive(true);
        course = courseRepository.save(course);
        return CourseMapper.toDto(course);
    }

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
    public boolean enrollUserInCourse(Long courseId, Long userId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null || !course.isActive()) {
            return false;
        }
        course.getStudents().add(userId);
        courseRepository.save(course);
        return true;
    }

    @Override
    public boolean unrollUserFromCourse(Long courseId, Long userId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null || !course.isActive()) {
            return false;
        }
        if (course.getStudents().remove(userId)) {
            courseRepository.save(course);
            return true;
        }
        return false;
    }
}
