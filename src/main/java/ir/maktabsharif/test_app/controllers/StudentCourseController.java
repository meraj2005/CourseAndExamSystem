package ir.maktabsharif.test_app.controllers;

import ir.maktabsharif.test_app.dto.CourseResponse;
import ir.maktabsharif.test_app.service.CourseService;
import ir.maktabsharif.test_app.service.impl.CourseServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/student/course")
@PreAuthorize("hasRole('STUDENT')")
public class StudentCourseController {

    private final CourseService courseService;

    public StudentCourseController(CourseServiceImpl courseService){
        this.courseService=courseService;
    }

    @GetMapping
    ResponseEntity<List<CourseResponse>> myCourses(){
        return ResponseEntity.ok(courseService.getMyStudentCourse());
    }
}
