package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.entity.AccountMovement;
import edu.uade.cookingrecipes.entity.Classroom;
import edu.uade.cookingrecipes.entity.Course;
import edu.uade.cookingrecipes.entity.Site;
import edu.uade.cookingrecipes.dto.request.CourseRequestDto;
import edu.uade.cookingrecipes.dto.response.CourseResponseDto;
import edu.uade.cookingrecipes.mapper.CourseMapper;
import edu.uade.cookingrecipes.model.AuthenticationModel;
import edu.uade.cookingrecipes.model.UserModel;
import edu.uade.cookingrecipes.repository.*;
import edu.uade.cookingrecipes.service.CourseService;
import edu.uade.cookingrecipes.service.validations.CourseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private CourseValidator courseValidator;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private AccountMovementRepository accountMovementRepository;

    @Override
    public CourseResponseDto createCourse(CourseRequestDto courseDto) {
        Course course = CourseMapper.toEntity(courseDto);
        Site site = siteRepository
                .findById(courseDto.getSiteId())
                .orElseThrow(() -> new NoSuchElementException("invalid site id"));
        course.setSite(site);
        course.setActive(true);
        List<Course> existingCourses = courseRepository.findAll();

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
    public List<CourseResponseDto> getAllCoursesBySiteId(Long siteId) {
        return courseRepository
                .findBySite_Id(siteId)
                .orElse(new ArrayList<>())
                .stream()
                .map(CourseMapper::toDto)
                .toList();
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
            throw new IllegalArgumentException("Curso no encontrado o inactivo.");
        }
        if (course.getStudents().size() >= course.getMaxParticipants()) {
            throw new IllegalArgumentException("El curso ha alcanzado el número máximo de participantes.");
        }
        UserModel user = getUser();

        AccountMovement movement = new AccountMovement();
        movement.setDateTime(java.time.LocalDateTime.now());

        if (user.getAccountBalance() <= 0.0) {
            // Si el usuario paga con tarjeta
            user.setAccountBalance(0.0);
            movement.setAmount(course.getPrice());
            movement.setReason("Pago con tarjeta");
        } else if (course.getPrice() > user.getAccountBalance()){
            // Si el usuario paga con saldo en cuenta
            movement.setAmount(user.getAccountBalance());
            movement.setReason("Pago con saldo en cuenta");
            AccountMovement cardMovement = new AccountMovement();
            cardMovement.setDateTime(java.time.LocalDateTime.now());
            cardMovement.setAmount(course.getPrice() - user.getAccountBalance());
            user.setAccountBalance(0.0);
            cardMovement.setReason("Pago con tarjeta");
            cardMovement.setUser(user);
            accountMovementRepository.save(cardMovement);
        } else {
            // Si el usuario paga con saldo en cuenta
            movement.setAmount(course.getPrice());
            movement.setReason("Pago con saldo en cuenta");
            user.setAccountBalance(user.getAccountBalance() - course.getPrice());
        }

        movement.setUser(user);
        accountMovementRepository.save(movement);
        course.getStudents().add(user.getId());
        courseRepository.save(course);
        return true;
    }
    @Override
    public boolean unrollUserFromCourse(Long courseId, boolean accountBalanceRefund) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null || !course.isActive()) {
            throw new IllegalArgumentException("Curso no encontrado o inactivo.");
        }
        UserModel user = getUser();
        AccountMovement movement = new AccountMovement();
        movement.setDateTime(java.time.LocalDateTime.now());
        double refundAmount = calculateRefundAmount(course);

        if (accountBalanceRefund) {
            user.setAccountBalance(user.getAccountBalance() + refundAmount);
            movement.setAmount(refundAmount);
            movement.setReason("Reintegro a cuenta");
        } else {
            movement.setAmount(refundAmount);
            movement.setReason("Reintegro a tarjeta");
        }

        movement.setUser(user);
        if (course.getStudents().remove(user.getId())) {
            accountMovementRepository.save(movement);
            courseRepository.save(course);
            return true;
        }
        return false;
    }

    private double calculateRefundAmount(Course course) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = course.getStartDate();
        LocalDate nineDaysBefore = startDate.minusDays(9);

        if (today.isBefore(nineDaysBefore)) {
            return course.getPrice(); // 100% de reintegro
        } else if (!today.isAfter(startDate.minusDays(1))) {
            return course.getPrice() * 0.7; // 70% de reintegro
        } else if (today.isEqual(startDate)) {
            return course.getPrice() * 0.5; // 50% de reintegro
        } else {
            return 0.0; // No reintegro si ya comenzó el curso
        }
    }

    private UserModel getUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserModel user = authenticationRepository.findByEmail(email)
                .map(AuthenticationModel::getUser)
                .orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("User not found: " + email);
        }
        return user;
    }
}
