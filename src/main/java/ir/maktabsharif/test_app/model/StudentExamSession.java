package ir.maktabsharif.test_app.model;

import ir.maktabsharif.test_app.model.enums.ExamSessionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "student_exam_session",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"student_id", "exam_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentExamSession extends BaseModel<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private ExamSessionStatus status;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(endTime);
    }
    private double score=0;
}
