package ir.maktabsharif.test_app.model.questions;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class MultipleChoiceQuestions extends Question{

    @ElementCollection
    @CollectionTable(
            name = "mcq_options",
            joinColumns = @JoinColumn(name = "question_id")
    )
    private List<Option> options = new ArrayList<>();

}
