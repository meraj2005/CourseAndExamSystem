package ir.maktabsharif.test_app.model;

import ir.maktabsharif.test_app.model.questions.Question;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
        name = "student_answer_draft",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"session_id", "question_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentAnswerDraft extends BaseModel<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private StudentExamSession session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ElementCollection
    @CollectionTable(
            name = "student_answer_draft_answers",
            joinColumns = @JoinColumn(name = "draft_id")
    )
    @Column(name = "answer")
    private List<String> answer;

    private LocalDateTime lastUpdated;

    private double score;
}
