package ir.maktabsharif.test_app.model.questions;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class AnnotationQuestions  extends Question{
    @Column(length = 4000)
    private String answerPlaceholder;
}
