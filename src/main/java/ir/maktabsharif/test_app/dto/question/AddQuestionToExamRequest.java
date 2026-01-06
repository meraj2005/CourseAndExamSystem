package ir.maktabsharif.test_app.dto.question;

import lombok.Data;

@Data
public class AddQuestionToExamRequest {
    private Long questionId;
    private Double score;
}
