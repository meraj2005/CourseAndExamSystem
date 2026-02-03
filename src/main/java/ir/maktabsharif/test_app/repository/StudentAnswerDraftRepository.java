package ir.maktabsharif.test_app.repository;

import ir.maktabsharif.test_app.model.StudentAnswerDraft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentAnswerDraftRepository extends JpaRepository<StudentAnswerDraft, Long> {

    Optional<StudentAnswerDraft> findBySessionIdAndQuestionId(Long sessionId, Long questionId);

    List<StudentAnswerDraft> findBySessionId(Long sessionId);


}
