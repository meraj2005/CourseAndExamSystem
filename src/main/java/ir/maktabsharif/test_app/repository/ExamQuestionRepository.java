package ir.maktabsharif.test_app.repository;

import ir.maktabsharif.test_app.model.Exam;
import ir.maktabsharif.test_app.model.ExamQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Long> {
    Optional<ExamQuestion>findByExamIdAndQuestionId(Long examId, Long questionId);
    void deleteExamQuestionByExamIdAndQuestionId(Long examId, Long questionId);
    List<ExamQuestion> findByExamId(Long examId);
    boolean existsByExamIdAndQuestionId(Long examId, Long questionId);
}
