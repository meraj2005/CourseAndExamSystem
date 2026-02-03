package ir.maktabsharif.test_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ir.maktabsharif.test_app.model.enums.EventStatus;
import ir.maktabsharif.test_app.model.questions.Question;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "exams")
public class Exam extends BaseModel<Long>{
    @Column(nullable = false)
    private String examCode;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Integer durationMinutes;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;

    @Enumerated(value = EnumType.STRING)
    private EventStatus eventStatus;

    @ManyToMany
    @JoinTable(
            name = "exam_students",
            joinColumns = @JoinColumn(name = "exam_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"exam_id", "student_id"})
    )
    private Set<User> students = new HashSet<>();

}
