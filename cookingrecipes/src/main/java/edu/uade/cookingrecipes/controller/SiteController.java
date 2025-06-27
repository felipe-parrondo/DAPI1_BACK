package edu.uade.cookingrecipes.controller;

import edu.uade.cookingrecipes.dto.request.SiteRequestDto;
import edu.uade.cookingrecipes.dto.response.SiteResponseDto;
import edu.uade.cookingrecipes.dto.response.attendance.CourseAttendanceResponseDto;
import edu.uade.cookingrecipes.dto.response.attendance.SiteAttendanceResponseDto;
import edu.uade.cookingrecipes.service.AttendanceService;
import edu.uade.cookingrecipes.service.SiteService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Site Operations")
@Validated
@RestController
@RequestMapping("/site")
@RequiredArgsConstructor
public class SiteController {

    @Autowired
    private SiteService siteService;
    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/") // get all courses
    public ResponseEntity<List<SiteResponseDto>> getAllSites() {
        List<SiteResponseDto> sites = siteService.getAllSites();
        return new ResponseEntity<>(sites, HttpStatus.OK);
    }

    @GetMapping("/{siteId}") // get site by id
    public ResponseEntity<SiteResponseDto> getSiteById(@PathVariable Long siteId) {
        SiteResponseDto site = siteService.getSiteById(siteId);
        if (site != null) {
            return new ResponseEntity<>(site, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create") // create a new site
    public ResponseEntity<SiteResponseDto> createSite(@RequestBody SiteRequestDto siteDto) {
        SiteResponseDto createdSite = siteService.createSite(siteDto);
        if (createdSite != null) {
            return new ResponseEntity<>(createdSite, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{siteId}") // delete a site by id
    public ResponseEntity<Void> deleteSite(@PathVariable Long siteId) {
        boolean deleted = siteService.deleteSite(siteId);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{userId}/{siteId}/register_attendance") //Registrar asistencia de un usuario a la sede
    public ResponseEntity<SiteAttendanceResponseDto> registerAttendance(@PathVariable Long userId,
                                                                          @PathVariable Long siteId) {
        SiteAttendanceResponseDto attendance = attendanceService.registerSiteAttendance(userId, siteId);
        if (attendance != null) {
            return new ResponseEntity<>(attendance, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{userId}/{siteId}/attendance") //Obtener asistencia de un usuario a la sede
    public ResponseEntity<SiteAttendanceResponseDto> getUserAttendanceInCourse(@PathVariable Long userId,
                                                                                 @PathVariable Long siteId) {
        SiteAttendanceResponseDto attendance = attendanceService.getUserAttendanceInSite(userId, siteId);
        if (attendance != null) {
            return new ResponseEntity<>(attendance, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
