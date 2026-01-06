package ir.maktabsharif.test_app.service.impl;

import ir.maktabsharif.test_app.dto.question.AddQuestionToExamRequest;
import ir.maktabsharif.test_app.dto.question.QuestionCreateRequest;
import ir.maktabsharif.test_app.dto.question.QuestionResponse;
import ir.maktabsharif.test_app.exceptions.BusinessException;
import ir.maktabsharif.test_app.model.Exam;
import ir.maktabsharif.test_app.model.ExamQuestion;
import ir.maktabsharif.test_app.model.User;
import ir.maktabsharif.test_app.model.questions.Question;
import ir.maktabsharif.test_app.model.questions.QuestionFactory;
import ir.maktabsharif.test_app.repository.ExamQuestionRepository;
import ir.maktabsharif.test_app.repository.ExamRepository;
import ir.maktabsharif.test_app.repository.QuestionRepository;
import ir.maktabsharif.test_app.security.SecurityUtil;
import ir.maktabsharif.test_app.service.ExamQuestionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ExamQuestionServiceImpl extends BaseServiceImpl<ExamQuestion,Long>implements ExamQuestionService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final ExamQuestionRepository examQuestionRepository;
    private final QuestionFactory questionFactory;

    public ExamQuestionServiceImpl(ExamRepository examRepository, QuestionRepository questionRepository, ExamQuestionRepository examQuestionRepository, QuestionFactory questionFactory) {
        super(examQuestionRepository);
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
        this.examQuestionRepository = examQuestionRepository;
        this.questionFactory = questionFactory;
    }


    @Override
    public QuestionResponse addQuestionToExam(Long examId, QuestionCreateRequest request) {
        return null;
    }

    @Override
    public void addExistingQuestionToExam(Long examId, Long questionId) {

    }

    @Override
    public QuestionResponse updateQuestion(Long questionId, QuestionCreateRequest request) {
        return null;
    }

    @Override
    public void removeQuestionFromExam(Long examId, Long questionId) {

    }

    @Override
    public void setDefaultScore(Long examId, Long questionId, Double score) {

    }

    @Override
    public List<QuestionResponse> getExamQuestions(Long examId) {
        return List.of();
    }

    @Override
    public Double calculateTotalScore(Long examId) {
        return 0.0;
    }

    @Override
    public void addFromBank(Long examId, AddQuestionToExamRequest request) {

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new BusinessException("Exam not found"));

        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new BusinessException("Question not found"));

        boolean alreadyAdded = examQuestionRepository
                .existsByExamIdAndQuestionId(examId, request.getQuestionId());

        if (alreadyAdded) {
            throw new BusinessException("Question already added to this exam");
        }

        ExamQuestion examQuestion = ExamQuestion.builder()
                .exam(exam)
                .question(question)
                .score(request.getScore())
                .build();

        examQuestionRepository.save(examQuestion);
    }

    @Override
    public void addNewQuestion(Long examId, QuestionCreateRequest request) {

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new BusinessException("Exam not found"));

        Question question = questionFactory.create(request,exam.getCourse().getTeacher(),exam.getCourse());

        question.setCourse(exam.getCourse());

        questionRepository.save(question);

        ExamQuestion examQuestion = ExamQuestion.builder()
                .exam(exam)
                .question(question)
                .score(0.0)
                .build();

        examQuestionRepository.save(examQuestion);


    }

}