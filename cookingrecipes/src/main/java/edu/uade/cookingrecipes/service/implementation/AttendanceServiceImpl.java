package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.dto.request.AttendanceRequestDto;
import edu.uade.cookingrecipes.dto.response.AttendanceResponseDto;
import edu.uade.cookingrecipes.entity.Course;
import edu.uade.cookingrecipes.entity.embeddable.Schedule;
import edu.uade.cookingrecipes.model.AuthenticationModel;
import edu.uade.cookingrecipes.model.UserModel;
import edu.uade.cookingrecipes.repository.*;
import edu.uade.cookingrecipes.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Override
    public AttendanceResponseDto registerAttendance(AttendanceRequestDto attendanceDto) {
        AttendanceResponseDto attendanceResponse = new AttendanceResponseDto();
        Course course = courseRepository.findById(attendanceDto.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + attendanceDto.getCourseId()));
        UserModel user = getUser();
        Schedule schedule = course.getSchedule();
        LocalDate PRUEBADIA = LocalDate.of(2024, 9, 1); // Fecha de prueba para el ejemplo
        LocalTime PRUEBAHORA = LocalTime.of(13, 0); // Hora de inicio de la clase
        List<LocalDate> classDates = getCourseDates(course);

        if (!classDates.contains(PRUEBADIA)) {
            throw new IllegalArgumentException("No hay clases hoy para el curso: " + course.getName());
        }

        if (attendanceDto.isPresentSite() && !attendanceDto.isPresentClassroom()) {
            attendanceResponse.setClassroomId(null);
            attendanceResponse.setSiteId(attendanceDto.getSiteId());
            attendanceResponse.setPresentSite(true);
            attendanceResponse.setPresentClassroom(false);
        } else if(attendanceDto.isPresentClassroom()) {
            if (schedule.getStartTime().isAfter(PRUEBAHORA) || schedule.getEndTime().isBefore(PRUEBAHORA)) {
                throw new IllegalArgumentException("Fuera del horario de clase para el curso: " + course.getName());
            }
            attendanceResponse.setClassroomId(attendanceDto.getClassroomId());
            attendanceResponse.setSiteId(attendanceDto.getSiteId());
            attendanceResponse.setPresentSite(true);
            attendanceResponse.setPresentClassroom(true);

        }

        attendanceResponse.setCourseId(attendanceDto.getCourseId());
        attendanceResponse.setUserId(user.getId());
        attendanceResponse.setPresenceDateTime(LocalDateTime.now().toString());
        return attendanceResponse;
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

    private List<LocalDate> getCourseDates(Course course) {
        List<LocalDate> classDates = new ArrayList<>();

        if (course.getStartDate() == null || course.getEndDate() == null || course.getSchedule() == null) {
            return classDates;
        }

        LocalDate startDate = course.getStartDate();

        while (!startDate.isAfter(course.getEndDate())) {
            classDates.add(startDate);
            startDate = startDate.plusWeeks(1);
        }

        return classDates;
    }
}
