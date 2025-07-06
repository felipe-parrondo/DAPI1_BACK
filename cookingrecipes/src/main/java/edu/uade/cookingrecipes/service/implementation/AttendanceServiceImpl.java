package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.dto.request.AttendanceRequestDto;
import edu.uade.cookingrecipes.dto.response.AttendanceResponseDto;
import edu.uade.cookingrecipes.entity.AttendanceRecord;
import edu.uade.cookingrecipes.entity.Classroom;
import edu.uade.cookingrecipes.entity.Course;
import edu.uade.cookingrecipes.entity.Site;
import edu.uade.cookingrecipes.entity.embeddable.Schedule;
import edu.uade.cookingrecipes.model.AuthenticationModel;
import edu.uade.cookingrecipes.model.UserModel;
import edu.uade.cookingrecipes.repository.*;
import edu.uade.cookingrecipes.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
            return null;
        }

        if (attendanceDto.isPresentClassroom()) {
            if (schedule.getStartTime().isAfter(LocalTime.now()) || schedule.getEndTime().isBefore(LocalTime.now())) {
                return null;
            }
            findOrCreateClassroomFromQr(attendanceDto);
        }

        AttendanceRecord record = attendanceRecordRepository
                .findAll().stream()
                .filter(r -> r.getDate().equals(LocalDate.now()) && r.getCourseId().equals(attendanceDto.getCourseId()) && r.getUserId().equals(user.getId()))
                .findFirst()
                .orElseGet(() -> AttendanceRecord.builder()
                        .courseId(attendanceDto.getCourseId())
                        .userId(user.getId())
                        .date(LocalDate.now())
                        .presentSite(false)
                        .presentClassroom(false)
                        .counted(false)
                        .build()
                );

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
        attendanceResponse.setPresentSite(record.isPresentSite());
        attendanceResponse.setPresentClassroom(record.isPresentClassroom());

        return attendanceResponse;
    }

    @Override // Obtiene la asistencia del usuario logeado en un curso
    public List<AttendanceResponseDto> getAttendanceForACourse(Long courseId) {
        UserModel user = getUser();
        List<AttendanceRecord> records = attendanceRecordRepository.findByCourseIdAndUserId(courseId, user.getId());

        if (records.isEmpty())
            return new ArrayList<>();
        else
            return getAttendanceResponseDtos(records);
    }

    private static List<AttendanceResponseDto> getAttendanceResponseDtos(List<AttendanceRecord> records) {
        List<AttendanceResponseDto> attendanceResponses = new ArrayList<>();
        for (AttendanceRecord record : records) {
            AttendanceResponseDto response = new AttendanceResponseDto();
            response.setCourseId(record.getCourseId());
            response.setUserId(record.getUserId());
            response.setPresenceDateTime(record.getDate().atStartOfDay().toString());
            response.setPresentSite(record.isPresentSite());
            response.setPresentClassroom(record.isPresentClassroom());
            attendanceResponses.add(response);
        }
        return attendanceResponses;
    }

    private UserModel getUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return authenticationRepository.findByEmail(email)
                .map(AuthenticationModel::getUser)
                .orElseThrow(() -> new NoSuchElementException("user not found"));
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

        Site site;

        if (siteId != null) {
            site = siteRepository.findById(siteId)
                    .orElseGet(() -> siteRepository.findById(1L).get());
        } else {
            site = siteRepository.findById(1L).get();
        }

        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseGet(() -> {
                    Classroom newClass = Classroom.builder()
                            .id(classroomId)
                            .classNumber(classroomId.intValue())
                            .site(site)
                            .build();

                    return classroomRepository.save(newClass);
                });

        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new NoSuchElementException("Curso no encontrado con ID: " + courseId));

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
