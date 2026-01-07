package ir.maktabsharif.test_app.dto.question;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class QuestionCreateRequest {

    @NotNull(message = "")
    private String type;

    @NotNull
    private String title;

    @NotNull
    @Size(max = 2000,message = "The question text should not exceed 2000 characters.")
    private String text;

    @NotNull
    private Double score;

    private List<OptionRequest> options;
}