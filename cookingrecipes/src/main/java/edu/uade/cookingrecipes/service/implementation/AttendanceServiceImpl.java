package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.dto.request.AttendanceRequestDto;
import edu.uade.cookingrecipes.dto.response.AttendanceResponseDto;
import edu.uade.cookingrecipes.entity.AttendanceRecord;
import edu.uade.cookingrecipes.entity.Classroom;
import edu.uade.cookingrecipes.entity.Course;
import edu.uade.cookingrecipes.entity.Site;
import edu.uade.cookingrecipes.entity.embeddable.Attendance;
import edu.uade.cookingrecipes.entity.embeddable.Schedule;
import edu.uade.cookingrecipes.model.AuthenticationModel;
import edu.uade.cookingrecipes.model.UserModel;
import edu.uade.cookingrecipes.repository.*;
import edu.uade.cookingrecipes.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    @Override
    public AttendanceResponseDto registerAttendance(AttendanceRequestDto attendanceDto) {
        AttendanceResponseDto attendanceResponse = new AttendanceResponseDto();

        Course course = courseRepository.findById(attendanceDto.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + attendanceDto.getCourseId()));

        UserModel user = getUser();
        Schedule schedule = course.getSchedule();
        List<LocalDate> classDates = getCourseDates(course);

        if (!classDates.contains(LocalDate.now())) {
            throw new IllegalArgumentException("No hay clases hoy para el curso: " + course.getName());
        }

        if (attendanceDto.isPresentClassroom()) {
            if (schedule.getStartTime().isAfter(LocalTime.now()) || schedule.getEndTime().isBefore(LocalTime.now())) {
                throw new IllegalArgumentException("Fuera del horario de clase para el curso: " + course.getName());
            }
            findOrCreateClassroomFromQr(attendanceDto);
        }

        AttendanceRecord record = attendanceRecordRepository
                .findByCourseIdAndUserIdAndDate(attendanceDto.getCourseId(), user.getId(), LocalDate.now())
                .orElseGet(() -> AttendanceRecord.builder()
                        .courseId(attendanceDto.getCourseId())
                        .userId(user.getId())
                        .date(LocalDate.now())
                        .presentSite(false)
                        .presentClassroom(false)
                        .counted(false)
                        .build());

        if (attendanceDto.isPresentSite()) {
            record.setPresentSite(true);
        }
        if (attendanceDto.isPresentClassroom()) {
            record.setPresentClassroom(true);
        }

        attendanceRecordRepository.save(record);

        attendanceResponse.setCourseId(attendanceDto.getCourseId());
        attendanceResponse.setUserId(user.getId());
        attendanceResponse.setPresenceDateTime(LocalDateTime.now().toString());
        attendanceResponse.setSiteId(attendanceDto.getSiteId());
        attendanceResponse.setClassroomId(attendanceDto.isPresentClassroom() ? attendanceDto.getClassroomId() : null);
        attendanceResponse.setPresentSite(record.isPresentSite());
        attendanceResponse.setPresentClassroom(record.isPresentClassroom());

        return attendanceResponse;
    }

    @Override
    public List<AttendanceResponseDto> getAttendanceForACourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + courseId));

        List<AttendanceRecord> attendanceRecords = attendanceRecordRepository.findByCourseId(courseId);
        List<AttendanceResponseDto> attendanceResponses = new ArrayList<>();

        for (AttendanceRecord record : attendanceRecords) {
            UserModel user = authenticationRepository.findById(record.getUserId())
                    .map(AuthenticationModel::getUser)
                    .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + record.getUserId()));

            AttendanceResponseDto response = new AttendanceResponseDto();
            response.setCourseId(courseId);
            response.setUserId(user.getId());
            response.setPresenceDateTime(record.getDate().atStartOfDay().toString());
            response.setSiteId(record.isPresentSite() ? course.getClassroom().getSite().getId() : null);
            response.setClassroomId(record.isPresentClassroom() ? course.getClassroom().getId() : null);
            response.setPresentSite(record.isPresentSite());
            response.setPresentClassroom(record.isPresentClassroom());

            attendanceResponses.add(response);
        }

        return attendanceResponses;
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

    private void findOrCreateClassroomFromQr(AttendanceRequestDto attendance) {
        Long classroomId = attendance.getClassroomId();
        Long siteId = attendance.getSiteId();
        Long courseId = attendance.getCourseId();

        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new RuntimeException("Sede no encontrada con ID: " + siteId));

        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseGet(() -> {
                    Classroom newClass = Classroom.builder()
                            .id(classroomId)
                            .classNumber("Aula-" + classroomId)
                            .site(site)
                            .build();

                    return classroomRepository.save(newClass);
                });

        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new RuntimeException("Curso no encontrado con ID: " + courseId));

        if (course.getClassroom() == null) {
            course.setClassroom(classroom);
            courseRepository.save(course);
        } else if (!course.getClassroom().equals(classroom)) {
            throw new RuntimeException("El aula del curso no coincide con el aula del QR escaneado.");
        }

        List<Classroom> classrooms = site.getClassrooms();
        if (!classrooms.contains(classroom)) {
            classrooms.add(classroom);
            site.setClassrooms(classrooms);
            siteRepository.save(site);
        }
    }
}
