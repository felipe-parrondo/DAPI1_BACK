package edu.uade.cookingrecipes.service.validations;

import edu.uade.cookingrecipes.entity.Course;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class CourseValidator {
    public void validate(Course course, List<Course> courses) {
        for (Course existingCourse : courses) {
            if (existingCourse.getName().equalsIgnoreCase(course.getName()) && existingCourse.isActive()) {
                throw new IllegalArgumentException("A course with the same name already exists: " + course.getName());
            }
        }
        if (course.getStartDate() != null && course.getEndDate() != null &&
                course.getStartDate().isAfter(course.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
        if (course.getStartDate() != null && course.getStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date cannot be in the past.");
        }
        if (course.getSchedule().getStartTime() != null && course.getSchedule().getEndTime() != null &&
                course.getSchedule().getStartTime().isAfter(course.getSchedule().getEndTime())) {
            throw new IllegalArgumentException("Start time cannot be after end time.");
        }
        if (course.getDuration() <= 0) {
            throw new IllegalArgumentException("Duration must be greater than zero.");
        }
        if (course.getMaxParticipants() <= 0) {
            throw new IllegalArgumentException("Max participants must be greater than zero.");
        }
        if (course.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        if (course.getDiscount() < 0 || course.getDiscount() > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100.");
        }
        if (course.getTeacherName() == null || course.getTeacherName().isEmpty()) {
            throw new IllegalArgumentException("Teacher name cannot be empty.");
        }
        if (course.getSite() == null) {
            throw new IllegalArgumentException("Site cannot be null.");
        }
        if (course.getMediaUrl() == null || course.getMediaUrl().isEmpty()) {
            throw new IllegalArgumentException("Media URL cannot be empty.");
        }
    }
}
