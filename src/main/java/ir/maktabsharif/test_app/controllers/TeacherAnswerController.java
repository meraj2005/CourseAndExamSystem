package ir.maktabsharif.test_app.controllers;

import ir.maktabsharif.test_app.dto.AnswerResponse;
import ir.maktabsharif.test_app.dto.user.UserResponse;
import ir.maktabsharif.test_app.service.StudentExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/answer")
@RequiredArgsConstructor
@PreAuthorize("hasRole('TEACHER')")
public class TeacherAnswerController {

    private final StudentExamService service;

    @GetMapping("{examId}/allStudent")
    public ResponseEntity<List<UserResponse>> allStudent(
                @PathVariable Long examId
            ){
        return ResponseEntity.ok(service.findByExamId(examId));
    }
    @GetMapping("{examId}/{studentId}")
    public ResponseEntity<List<AnswerResponse>> showAllAnswerExamStudent(
            @PathVariable Long examId,
            @PathVariable Long studentId
        ){
        return ResponseEntity.ok(service.showAllAnswerExamStudent(examId,studentId));
    }
    @PostMapping("{examId}")
    public ResponseEntity<Void> correctQuestion(
            @PathVariable Long examId,
            @RequestParam Long studentId,
            @RequestParam Long questionId,
            @RequestParam double score

    ){
        service.correctQuestion(examId,studentId,questionId,score);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{examId}/{studentId}/correctexam")
    public ResponseEntity<Double> correctExam(
            @PathVariable Long examId,
            @PathVariable Long studentId
    ){
        return ResponseEntity.ok(service.correctExam(examId,studentId));
    }

}
