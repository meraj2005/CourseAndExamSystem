package ir.maktabsharif.test_app.controllers;

import ir.maktabsharif.test_app.dto.CourseCreateRequest;
import ir.maktabsharif.test_app.dto.CourseParticipantsResponse;
import ir.maktabsharif.test_app.dto.CourseResponse;
import ir.maktabsharif.test_app.service.CourseService;
import ir.maktabsharif.test_app.service.impl.CourseServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/courses")
@PreAuthorize("hasRole('ADMIN')")
public class AdminCourseController {


    private final CourseService courseService;


    public AdminCourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @PostMapping
    public ResponseEntity<CourseResponse> create(@Valid @RequestBody CourseCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseService.create(request));
    }


    @PutMapping("/{courseId}/teacher/{teacherId}")
    public ResponseEntity<Void> assignTeacher(@PathVariable Long courseId,
                                              @PathVariable Long teacherId) {
        courseService.assignTeacher(courseId, teacherId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<Void> addStudent(@PathVariable Long courseId,
                                           @PathVariable Long studentId) {
        courseService.addStudent(courseId, studentId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{courseId}/participants")
    public ResponseEntity<CourseParticipantsResponse> participants(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getParticipants(courseId));
    }
}