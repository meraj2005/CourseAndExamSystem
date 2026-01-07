package ir.maktabsharif.test_app.service;

import ir.maktabsharif.test_app.dto.question.AddQuestionToExamRequest;
import ir.maktabsharif.test_app.dto.question.QuestionCreateRequest;
import ir.maktabsharif.test_app.dto.question.QuestionResponse;
import ir.maktabsharif.test_app.model.ExamQuestion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

import java.util.List;

public interface ExamQuestionService extends BaseService<ExamQuestion,Long>{
    QuestionResponse updateQuestion(Long questionId, QuestionCreateRequest request);
    void removeQuestionFromExam(Long examId, Long questionId);
    List<QuestionResponse> getExamQuestions(Long examId);
    Double calculateTotalScore(Long examId);
    void addFromBank(Long examId, AddQuestionToExamRequest request);
    void addNewQuestion(Long examId, QuestionCreateRequest request);
}
