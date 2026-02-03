package ir.maktabsharif.test_app.controllers;

import ir.maktabsharif.test_app.dto.RemainingTimeResponse;
import ir.maktabsharif.test_app.dto.SaveAnswerRequest;
import ir.maktabsharif.test_app.dto.StartExamResponse;
import ir.maktabsharif.test_app.dto.question.QuestionResponse;
import ir.maktabsharif.test_app.service.CourseService;
import ir.maktabsharif.test_app.service.ExamService;
import ir.maktabsharif.test_app.service.StudentExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/exams")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
public class StudentExamController {

    private final StudentExamService service;

    @PostMapping("/{examId}/start")
    public ResponseEntity<StartExamResponse> startExam(
            @PathVariable Long examId
    ) {
        return ResponseEntity.ok(service.startExam(examId));
    }

    @GetMapping("/{examId}/remaining-time")
    public ResponseEntity<RemainingTimeResponse> remainingTime(
            @PathVariable Long examId
    ) {
        return ResponseEntity.ok(service.getRemainingTime(examId));
    }

    @PostMapping("/{examId}/answer")
    public ResponseEntity<Void> saveAnswer(
            @PathVariable Long examId,
            @RequestBody @Valid SaveAnswerRequest request
    ) {
        service.saveAnswer(examId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{examId}/submit")
    public ResponseEntity<Void> submitExam(
            @PathVariable Long examId
    ) {
        service.submitExam(examId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{examId}/questions")
    public ResponseEntity<List<QuestionResponse>> questions(
            @PathVariable Long examId
    ) {
        return ResponseEntity.ok(service.getExamQuestions(examId));
    }
    @GetMapping("{examId}/score")
    public ResponseEntity<Double> correctExam(
            @PathVariable Long examId
    ){
        return ResponseEntity.ok(service.correctExamStudent(examId));
    }
}
