package ir.maktabsharif.test_app.service;

import ir.maktabsharif.test_app.dto.AnswerResponse;
import ir.maktabsharif.test_app.dto.RemainingTimeResponse;
import ir.maktabsharif.test_app.dto.SaveAnswerRequest;
import ir.maktabsharif.test_app.dto.StartExamResponse;
import ir.maktabsharif.test_app.dto.question.QuestionResponse;
import ir.maktabsharif.test_app.dto.user.UserResponse;
import ir.maktabsharif.test_app.model.StudentAnswerDraft;
import ir.maktabsharif.test_app.model.StudentExamSession;

import java.util.List;

public interface StudentExamService {

    StartExamResponse startExam(Long examId);

    RemainingTimeResponse getRemainingTime(Long examId);

    void saveAnswer(Long examId, SaveAnswerRequest request);

    void submitExam(Long examId);

    List<QuestionResponse> getExamQuestions(Long examId);

    List<UserResponse> findByExamId(Long examId);

    List<AnswerResponse> showAllAnswerExamStudent(Long examId, Long studentId);

    void correctQuestion(Long examId, Long studentId,Long questionId,double score);

    Double correctExam(Long examId, Long studentId);

    Double correctExamStudent(Long examId);
}
