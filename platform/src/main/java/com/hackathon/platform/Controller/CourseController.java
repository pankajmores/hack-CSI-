package com.hackathon.platform.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.platform.DTO.CourseDTO;
import com.hackathon.platform.POJOS.Course;
import com.hackathon.platform.Service.CourseService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/course")
@Slf4j
public class CourseController {

    @Autowired
    private CourseService courseService; // Assuming CourseService exists to handle logic

    @PostMapping("/create")
    public ResponseEntity<String> createCourse(@RequestBody CourseDTO course) {
        courseService.createCourse(course);
        return ResponseEntity.ok("Course created successfully");
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable String courseId) {
        Course course = courseService.getCourseById(courseId);
        return ResponseEntity.ok(course);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @PutMapping("/{courseId}/update")
    public ResponseEntity<String> updateCourse(@PathVariable String courseId, @RequestBody Course updatedCourse) {
        courseService.updateCourse(courseId, updatedCourse);
        return ResponseEntity.ok("Course updated successfully");
    }

    @DeleteMapping("/{courseId}/delete")
    public ResponseEntity<String> deleteCourse(@PathVariable String courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok("Course deleted successfully");
    }

    @PostMapping("/{courseId}/enroll")
    public ResponseEntity<String> enrollUser(@PathVariable String courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        String email = authentication.getName(); // Assumes principal is email
        log.info(email);
        courseService.enrollUserInCourse(courseId, email);
        
        return ResponseEntity.ok("User enrolled successfully in course " + courseId);
    }
}

