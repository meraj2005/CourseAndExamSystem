package ir.maktabsharif.test_app.repository;

import ir.maktabsharif.test_app.model.Exam;
import ir.maktabsharif.test_app.model.StudentExamSession;
import ir.maktabsharif.test_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudentExamSessionRepository
        extends JpaRepository<StudentExamSession, Long> {
    Optional<StudentExamSession> findByStudentIdAndExamId(Long id, Long examId);
    Optional<StudentExamSession> findByStudentAndExam(User student, Exam exam);
    List<StudentExamSession> findByExamId(Long examId);
    @Query("""
        select s from StudentExamSession s
        where s.status = 'IN_PROGRESS'
        and s.endTime < :now
    """)
    List<StudentExamSession> findExpiredSessions(@Param("now") LocalDateTime now);
}
