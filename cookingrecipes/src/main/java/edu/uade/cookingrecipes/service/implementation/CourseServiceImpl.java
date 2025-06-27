package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.entity.Course;
import edu.uade.cookingrecipes.entity.Site;
import edu.uade.cookingrecipes.dto.request.CourseRequestDto;
import edu.uade.cookingrecipes.dto.response.CourseResponseDto;
import edu.uade.cookingrecipes.mapper.CourseMapper;
import edu.uade.cookingrecipes.model.AuthenticationModel;
import edu.uade.cookingrecipes.model.UserModel;
import edu.uade.cookingrecipes.repository.AuthenticationRepository;
import edu.uade.cookingrecipes.repository.CourseRepository;
import edu.uade.cookingrecipes.repository.SiteRepository;
import edu.uade.cookingrecipes.service.CourseService;
import edu.uade.cookingrecipes.service.validations.CourseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private CourseValidator courseValidator;
    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Override
    public CourseResponseDto createCourse(CourseRequestDto courseDto) {
        Course course = CourseMapper.toEntity(courseDto);
        List<Course> existingCourses = courseRepository.findAll();

        Site site = siteRepository.findById(courseDto.getSiteId())
                .orElseThrow(() -> new IllegalArgumentException("Site not found with id: " + courseDto.getSiteId()));
        course.setSite(site);
        course.setActive(true);

        courseValidator.validate(course, existingCourses);

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
    public boolean enrollUserInCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null || !course.isActive()) {
            return false;
        }
        Long userId = getUserId();
        course.getStudents().add(userId);
        courseRepository.save(course);
        return true;
    }

    @Override
    public boolean unrollUserFromCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null || !course.isActive()) {
            return false;
        }
        Long userId = getUserId();
        if (course.getStudents().remove(userId)) {
            courseRepository.save(course);
            return true;
        }
        return false;
    }

    private Long getUserId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserModel user = authenticationRepository.findByEmail(email)
                .map(AuthenticationModel::getUser)
                .orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("User not found: " + email);
        }
        return user.getId();
    }
}
