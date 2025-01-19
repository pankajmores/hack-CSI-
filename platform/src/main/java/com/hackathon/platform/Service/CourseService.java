package com.hackathon.platform.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathon.platform.DTO.CourseDTO;
import com.hackathon.platform.POJOS.Course;
import com.hackathon.platform.POJOS.Expert;
import com.hackathon.platform.POJOS.Intern;
import com.hackathon.platform.Repositry.CourseRepo;
import com.hackathon.platform.Repositry.ExpertRepo;
import com.hackathon.platform.Repositry.InterRepo;

@Service
public class CourseService {

    @Autowired
    private CourseRepo courseRepository; // Assuming a MongoDB repository
    @Autowired
    private InterRepo internRepository;

    public void createCourse(CourseDTO course) {
        Course  course2 = new Course();
        course2.setCourseId(UUID.randomUUID().toString());
        course2.setCourseDescription(course.getCourseDescription());
        course2.setCourseName(course.getCourseName());
        course2.setCourseTitle(course.getCourseTitle());
        course2.setVideo_link(course.getVideo_link());
        courseRepository.save(course2);
    }

    public Course getCourseById(String courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public void updateCourse(String courseId, Course updatedCourse) {
        Course course = getCourseById(courseId);
        course.setCourseName(updatedCourse.getCourseName());
        course.setCourseTitle(updatedCourse.getCourseTitle());
        course.setCourseDescription(updatedCourse.getCourseDescription());
        courseRepository.save(course);
    }

    public void deleteCourse(String courseId) {
        courseRepository.deleteById(courseId);
    }

    public void enrollUserInCourse(String courseId, String email) {
        Course course = getCourseById(courseId);
        Intern intern = internRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Expert not found"));

        if (!intern.getCourses().contains(courseId)) {
            intern.getCourses().add(course);
            internRepository.save(intern);
        }

        if (!course.getInterns().contains(email)) {
            course.getInterns().add(intern);
            courseRepository.save(course);
        }
    }
}

