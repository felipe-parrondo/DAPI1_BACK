package edu.uade.cookingrecipes.controller;

import edu.uade.cookingrecipes.dto.request.AttendanceRequestDto;
import edu.uade.cookingrecipes.dto.response.AttendanceResponseDto;
import edu.uade.cookingrecipes.service.AttendanceService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Attendance Management", tags = "Attendance")
@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/") // Registrar asistencia de la sede/curso
    public ResponseEntity<AttendanceResponseDto> registerAttendance(@RequestBody AttendanceRequestDto attendanceRequestDto) {
        AttendanceResponseDto attendance = attendanceService.registerAttendance(attendanceRequestDto);
        if (attendance != null) {
            return new ResponseEntity<>(attendance, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
