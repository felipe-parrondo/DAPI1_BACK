package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.entity.*;
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

    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    @Override
    public CourseResponseDto createCourse(CourseRequestDto courseDto) {
        Course course = CourseMapper.toEntity(courseDto);
        course.setActive(true);
        List<Course> existingCourses = courseRepository.findAll();

        courseValidator.validate(course, existingCourses);

        course = courseRepository.save(course);
        return CourseMapper.toDto(course, "");
    }

    @Override
    public List<CourseResponseDto> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .filter(course -> course.getEndDate().isAfter(LocalDate.now()))
                .map(course -> CourseMapper.toDto(course, ""))
                .toList();
    }

    @Override
    public List<CourseResponseDto> getAllCoursesBySiteId(Long siteId) {
        return courseRepository
                .findBySiteId(siteId)
                .orElse(new ArrayList<>())
                .stream()
                .map(course -> CourseMapper.toDto(course, ""))
                .collect(Collectors.toList());
    }

    @Override
    public CourseResponseDto getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) throw new IllegalArgumentException("Curso no encontrado.");
        String attendance = getUserAttendanceForCourse(courseId);
        return CourseMapper.toDto(course, attendance);
    }

    private String getUserAttendanceForCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) throw new IllegalArgumentException("Curso no encontrado.");
        UserModel user = getUser();
        String attendance = calculateAttendance(courseId, user.getId());
        if (attendance == null) {
            throw new IllegalArgumentException("No se pudo calcular la asistencia.");
        }
        return attendance;
    }

    private String calculateAttendance(Long courseId, Long userId) {
        UserModel user = authenticationRepository.findById(userId)
                .map(AuthenticationModel::getUser)
                .orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("Usuario no encontrado.");
        }

        List<AttendanceRecord> attendanceRecords = attendanceRecordRepository
                .findByCourseIdAndUserId(courseId, user.getId());

        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            throw new IllegalArgumentException("Curso no encontrado.");
        }
        if (attendanceRecords.isEmpty()) {
            return "0%";
        }

        List<LocalDate> classDays = getDaysOfCourse(course);
        List<LocalDate> pastClassDays = classDays.stream()
                .filter(date -> !date.isAfter(LocalDate.now()))
                .toList();

        int totalPastClasses = pastClassDays.size();
        if (totalPastClasses == 0) {
            return "0%";
        }

        long attendedCount = attendanceRecords.stream()
                .filter(record -> record.getDate() != null && !record.getDate().isAfter(LocalDate.now()))
                .count();

        double attendancePercentage = (attendedCount * 100.0) / totalPastClasses;
        return String.format("%.2f%%", attendancePercentage);
    }

    private List<LocalDate> getDaysOfCourse(Course course) {
        if (course.getSchedule() == null || course.getStartDate() == null || course.getEndDate() == null) {
            return List.of();
        }

        List<LocalDate> classDays = new ArrayList<>();
        LocalDate startDate = course.getStartDate();
        LocalDate endDate = course.getEndDate();

        while (!startDate.isAfter(endDate)) {
            classDays.add(startDate);
            startDate = startDate.plusDays(1);
        }

        return classDays;
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
    public boolean enrollUserInCourseByAdmin(Long courseId, Long userId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null || !course.isActive()) {
            throw new IllegalArgumentException("Curso no encontrado o inactivo.");
        }
        UserModel user = authenticationRepository.findById(userId)
                .map(AuthenticationModel::getUser)
                .orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("Usuario no encontrado.");
        }

        if (course.getStudents().size() >= course.getMaxParticipants()) {
            throw new IllegalArgumentException("El curso ha alcanzado el número máximo de participantes.");
        }

        AccountMovement movement = new AccountMovement();
        movement.setDateTime(java.time.LocalDateTime.now());

        if (user.getAccountBalance() <= 0.0) {
            // Si el usuario paga en la sede
            user.setAccountBalance(0.0);
            movement.setAmount(course.getPrice());
            movement.setReason("Pago en sede");
        } else if (course.getPrice() > user.getAccountBalance()){
            // Si el usuario paga una parte con saldo en cuenta y otra en sede
            movement.setAmount(user.getAccountBalance());
            movement.setReason("Pago con saldo en cuenta");
            AccountMovement cardMovement = new AccountMovement();
            cardMovement.setDateTime(java.time.LocalDateTime.now());
            cardMovement.setAmount(course.getPrice() - user.getAccountBalance());
            user.setAccountBalance(0.0);
            cardMovement.setReason("Pago en sede");
            cardMovement.setUser(user);
            accountMovementRepository.save(cardMovement);
        } else {
            // Si el usuario paga con saldo en cuenta
            movement.setAmount(course.getPrice());
            movement.setReason("Pago con saldo en cuenta");
            user.setAccountBalance(user.getAccountBalance() - course.getPrice());
        }

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
