package ir.maktabsharif.test_app.controllers;

import ir.maktabsharif.test_app.dto.question.AddQuestionToExamRequest;
import ir.maktabsharif.test_app.dto.question.QuestionCreateRequest;
import ir.maktabsharif.test_app.service.ExamQuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher/exams/{examId}/questions")
@PreAuthorize("hasRole('TEACHER')")
@RequiredArgsConstructor
public class TeacherExamQuestionController {

    private final ExamQuestionService service;

    @PostMapping("/new")
    public ResponseEntity<Void> addNew(
            @PathVariable Long examId,
            @RequestBody @Valid QuestionCreateRequest request
    ) {
        service.addNewQuestion(examId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bank")
    public ResponseEntity<Void> addFromBank(
            @PathVariable Long examId,
            @RequestBody AddQuestionToExamRequest request
    ) {
        service.addFromBank(examId, request);
        return ResponseEntity.ok().build();
    }
}
