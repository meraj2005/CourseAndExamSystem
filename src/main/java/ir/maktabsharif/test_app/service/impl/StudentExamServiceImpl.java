package ir.maktabsharif.test_app.service.impl;

import ir.maktabsharif.test_app.dto.AnswerResponse;
import ir.maktabsharif.test_app.dto.RemainingTimeResponse;
import ir.maktabsharif.test_app.dto.SaveAnswerRequest;
import ir.maktabsharif.test_app.dto.StartExamResponse;
import ir.maktabsharif.test_app.dto.question.QuestionResponse;
import ir.maktabsharif.test_app.dto.user.UserResponse;
import ir.maktabsharif.test_app.exceptions.BusinessException;
import ir.maktabsharif.test_app.mapper.AnswerMapper;
import ir.maktabsharif.test_app.mapper.QuestionMapper;
import ir.maktabsharif.test_app.mapper.UserMapper;
import ir.maktabsharif.test_app.model.*;
import ir.maktabsharif.test_app.model.enums.ExamSessionStatus;
import ir.maktabsharif.test_app.model.enums.Status;
import ir.maktabsharif.test_app.model.questions.MultipleChoiceQuestions;
import ir.maktabsharif.test_app.model.questions.Option;
import ir.maktabsharif.test_app.model.questions.Question;
import ir.maktabsharif.test_app.repository.*;
import ir.maktabsharif.test_app.security.SecurityUtil;
import ir.maktabsharif.test_app.service.StudentExamService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentExamServiceImpl extends BaseServiceImpl<StudentExamSession,Long> implements StudentExamService {

    private final StudentExamSessionRepository sessionRepository;
    private final StudentAnswerDraftRepository draftRepository;
    private final ExamRepository examRepository;
    private final ExamQuestionRepository examQuestionRepository;
    private final QuestionRepository questionRepository;
    private final SecurityUtil securityUtil;
    private final UserRepository userRepository;

    public StudentExamServiceImpl(StudentExamSessionRepository sessionRepository, StudentAnswerDraftRepository draftRepository, ExamRepository examRepository, ExamQuestionRepository examQuestionRepository, QuestionRepository questionRepository, SecurityUtil securityUtil, UserRepository userRepository) {
        super(sessionRepository);
        this.sessionRepository = sessionRepository;
        this.draftRepository = draftRepository;
        this.examRepository = examRepository;
        this.examQuestionRepository = examQuestionRepository;
        this.questionRepository = questionRepository;
        this.securityUtil = securityUtil;
        this.userRepository = userRepository;
    }

    @Override
    public StartExamResponse startExam(Long examId) {

        User student = securityUtil.getCurrentUser();

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        StudentExamSession session = sessionRepository
                .findByStudentIdAndExamId(student.getId(), examId)
                .orElseGet(() -> createNewSession(student, exam));
        if (session.getStatus().equals(ExamSessionStatus.FINISHED)){
            throw new BusinessException("Exam finished");
        }
        long remaining = Duration.between(
                LocalDateTime.now(),
                session.getEndTime()
        ).getSeconds();

        if (remaining <= 0) {
            finishSession(session);
            throw new RuntimeException("Exam time is over");
        }

        return new StartExamResponse(
                session.getStartTime(),
                session.getEndTime(),
                remaining
        );
    }

    private StudentExamSession createNewSession(User student, Exam exam) {
        LocalDateTime now = LocalDateTime.now();

        StudentExamSession session = StudentExamSession.builder()
                .student(student)
                .exam(exam)
                .startTime(now)
                .endTime(now.plusMinutes(exam.getDurationMinutes()))
                .status(ExamSessionStatus.IN_PROGRESS)
                .build();

        return sessionRepository.save(session);
    }

    @Override
    public RemainingTimeResponse getRemainingTime(Long examId) {

        StudentExamSession session = getActiveSession(examId);

        long remaining = Duration.between(
                LocalDateTime.now(),
                session.getEndTime()
        ).getSeconds();

        if (remaining <= 0) {
            finishSession(session);
            remaining = 0;
        }

        return new RemainingTimeResponse(remaining);
    }

    @Override
    public void saveAnswer(Long examId, SaveAnswerRequest request) {

        StudentExamSession session = getActiveSession(examId);

        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        StudentAnswerDraft draft = draftRepository
                .findBySessionIdAndQuestionId(
                        session.getId(),
                        question.getId()
                )
                .orElse(
                        StudentAnswerDraft.builder()
                                .session(session)
                                .question(question)
                                .build()
                );

        draft.setAnswer(request.getAnswer());
        draft.setLastUpdated(LocalDateTime.now());

        draftRepository.save(draft);
    }

    @Override
    public void submitExam(Long examId) {
        StudentExamSession session = getActiveSession(examId);
        finishSession(session);
    }

    private void finishSession(StudentExamSession session) {
        session.setStatus(ExamSessionStatus.FINISHED);
        sessionRepository.save(session);

    }

    private StudentExamSession getActiveSession(Long examId) {
        User student = securityUtil.getCurrentUser();

        StudentExamSession session = sessionRepository
                .findByStudentIdAndExamId(student.getId(), examId)
                .orElseThrow(() -> new RuntimeException("Exam not started"));

        if (session.getStatus() != ExamSessionStatus.IN_PROGRESS) {
            throw new RuntimeException("Exam already finished");
        }

        if (session.isExpired()) {
            finishSession(session);
            throw new RuntimeException("Exam time expired");
        }

        return session;
    }

    @Override
    public List<QuestionResponse> getExamQuestions(Long examId) {
        return examQuestionRepository.findQuestionByExamId(examId)
                .stream()
                .map(QuestionMapper::toQuestionResponse)
                .toList();
    }

    @Override
    public List<UserResponse> findByExamId(Long examId) {
        return sessionRepository.findByExamId(examId).stream()
                .map(StudentExamSession::getStudent)
                .map(UserMapper::toDTO)
                .toList();
    }

    @Override
    public List<AnswerResponse> showAllAnswerExamStudent(Long examId, Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("user not fund"));
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("exam not fund"));
        StudentExamSession session=sessionRepository.findByStudentAndExam(student,exam)
                .orElseThrow(() -> new RuntimeException("Exam not fund or student is not in exam."));

        return draftRepository.findBySessionId(session.getId())
                .stream()
                .map(AnswerMapper::toDTO)
                .toList();
    }

    @Override
    public void correctQuestion(
            Long examId,
            Long studentId,
            Long questionId,
            double teacherScore
    ) {

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        StudentExamSession session = sessionRepository
                .findByStudentAndExam(student, exam)
                .orElseThrow(() ->
                        new RuntimeException("Student is not registered in this exam"));

        StudentAnswerDraft answerDraft = draftRepository
                .findBySessionIdAndQuestionId(session.getId(), questionId)
                .orElseThrow(() -> new RuntimeException("Answer not found"));

        Question question = answerDraft.getQuestion();

        ExamQuestion examQuestion = examQuestionRepository
                .findByExamIdAndQuestionId(exam.getId(), question.getId())
                .orElseThrow(() ->
                        new RuntimeException("Question not found in exam"));

        double finalScore;

        if (question instanceof MultipleChoiceQuestions mcq) {

            List<Option> correctOptions = mcq.getOptions().stream()
                    .filter(Option::isCorrect)
                    .toList();

            List<String> studentAnswers = answerDraft.getAnswer();

            int correctCount = 0;
            for (Option option : correctOptions) {
                if (studentAnswers.contains(option.getText())) {
                    correctCount++;
                }
            }

            double maxScore = examQuestion.getScore();
            finalScore = (maxScore * correctCount) / correctOptions.size();

        }

        else {

            if (teacherScore < 0 || teacherScore > examQuestion.getScore()) {
                throw new RuntimeException("Invalid score value");
            }

            finalScore = teacherScore;
        }

        answerDraft.setScore(finalScore);
        draftRepository.save(answerDraft);
    }

    @Override
    public Double correctExam(Long examId, Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("user not fund"));
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("exam not fund"));
        StudentExamSession session=sessionRepository.findByStudentAndExam(student,exam)
                .orElseThrow(() -> new RuntimeException("Exam not fund or student is not in exam."));

        return draftRepository.findBySessionId(session.getId())
                .stream()
                .mapToDouble(StudentAnswerDraft::getScore)
                .sum();

    }
    @Override
    public Double correctExamStudent(Long examId) {
        User student = securityUtil.getCurrentUser();
        StudentExamSession session=sessionRepository.findByStudentIdAndExamId(student.getId(),examId)
                .orElseThrow(() -> new RuntimeException("Exam not fund or student is not in exam."));

        return draftRepository.findBySessionId(session.getId())
                .stream()
                .mapToDouble(StudentAnswerDraft::getScore)
                .sum();

    }




}
